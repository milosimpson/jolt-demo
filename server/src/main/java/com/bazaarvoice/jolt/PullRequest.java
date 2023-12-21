package com.bazaarvoice.jolt;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

import com.bazaarvoice.jolt.helper.GitConstants;
import com.bazaarvoice.jolt.helper.GitService;

/**
 * Servlet implementation class PullRequest
 */
@WebServlet("/pullrequest")
public class PullRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//Update the path of jolt-specs / activity-serve local git location 
	private static final String BASE_REPO_PATH= "<jolt_spec>/.git";
	private Repository repository;

	private GitService gitService;
	/**
	 * @throws IOException 
	 * @see HttpServlet#HttpServlet()
	 */
	public PullRequest(){
		super();
		//TODO : Move to singleton
		this.gitService = new GitService(BASE_REPO_PATH);
		try {
			this.repository = gitService.getHostedRepository("");
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		String inputString, specString;
		try {
			inputString = req.getParameter("input");

			specString = req.getParameter("spec");

		} catch (Exception e) {
			response.getWriter().println("Could not url-decode the inputs.\n");
			return;
		}
		Object input, spec;

		try {
			input = JsonUtils.jsonToObject(inputString);
		} catch (Exception e) {
			response.getWriter().println("Could not parse the 'input' JSON.\n");
			return;
		}

		try {
			spec = JsonUtils.jsonToObject(specString);
		} catch (Exception e) {
			response.getWriter().println("Could not parse the 'spec' JSON.\n");
			return;
		}

		String branchName = this.raisePR("Test", GitConstants.GIT_REPOSITORY_NAME);
		System.out.println(branchName);
		String res = branchName;
		// Add PR details in the response
		response.getWriter().println(res);
	}

	private Repository findRepositoryByName(String repositoryName)
			throws IOException, URISyntaxException{
		 	
		return this.gitService.getHostedRepository("");
	}

	protected String raisePR(String branchName, String repoName) {
		//
		try {
			Repository r = findRepositoryByName(repoName);
			Git git = Git.wrap(r);
			if(git.branchList().call().stream().anyMatch(ref -> ref.getName().equals("refs/heads/"+branchName))) {
				return "Branch name Already exist";
			}
			
			CreateBranchCommand command = git.branchCreate();
			command.setName(branchName);
			command.call();
			r.close();
			return branchName;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (GitAPIException e) {
			throw new RuntimeException(e);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	
	}

}
