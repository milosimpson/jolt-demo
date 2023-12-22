package com.bazaarvoice.jolt;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.GitCommand;
import org.eclipse.jgit.api.PushCommand;
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
	// Update the path of jolt-specs / activity-serve local git location
	// private static final String BASE_REPO_PATH = "<jolt-specs_location>";
	private static final String BASE_REPO_PATH = "/Users/raraj/Documents/rspace/projects/freshservice/source/jolt-try/jolt-specs";
	private static final String GIT_BASE_REPO_PATH = BASE_REPO_PATH + "/.git";
	private Repository repository;

	private GitService gitService;

	/**
	 * @throws IOException
	 * @see HttpServlet#HttpServlet()
	 */
	public PullRequest() {
		super();
		// TODO : Move to singleton
		this.gitService = new GitService(GIT_BASE_REPO_PATH);
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
		String inputString, specString, modelName, branchName;
		try {
			inputString = req.getParameter("input");

			specString = req.getParameter("spec");
			modelName = req.getParameter("modelName");

			branchName = req.getParameter("branchName");

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

		String result = this.raisePR(branchName, GitConstants.GIT_REPOSITORY_NAME, JsonUtils.toPrettyJsonString(input),
				JsonUtils.toPrettyJsonString(spec), modelName);

		System.out.println(result);
		String res = branchName;
		// Add PR details in the response
		response.getWriter().println(res);
	}

	private Repository findRepositoryByName(String repositoryName) throws IOException, URISyntaxException {

		return this.gitService.getHostedRepository("");
	}

	protected String raisePR(String branchName, String repoName, String input, String spec, String modelName) {
		//
		try {
			Repository r = findRepositoryByName(repoName);
			Git git = Git.wrap(r);
			if (git.branchList().call().stream().anyMatch(ref -> ref.getName().equals("refs/heads/" + branchName))) {
				return "Branch name Already exist";
			}

			// Create Branch
			CreateBranchCommand createBranchCommand = git.branchCreate();
			createBranchCommand.setName(branchName);			
			

			// Git checkout
			CheckoutCommand checkoutCommand = git.checkout();
			checkoutCommand.setName(branchName);
			
			
			// File write
			Callable<Boolean> fileWrite = () -> {
				String inputFileName = modelName + "_input.json";
				String specFileName = modelName + "_spec.json";
				String filePath = BASE_REPO_PATH + "/" + "specs/";
				fileWrite(filePath, inputFileName, input);
				 fileWrite(filePath, specFileName, input);
				 return true;
			};
			
			// Git add
			AddCommand addCommand = git.add();
			addCommand.addFilepattern(".");
			
			// Git commit
			CommitCommand commitCommand = git.commit();
			commitCommand.setMessage("Onboard " + modelName);

			//Git Push
			PushCommand pushCommand = git.push();
			pushCommand.getPushDefault();
			
			ExecutorService executor = Executors.newSingleThreadExecutor();
			executor.submit(createBranchCommand).get();
			executor.submit(checkoutCommand).get();
			executor.submit(fileWrite).get();
			executor.submit(addCommand).get();
			executor.submit(commitCommand).get();
			//executor.submit(pushCommand).get();
			
			
			r.close();
			// Git push
			return branchName + " created successful";
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (GitAPIException e) {
			throw new RuntimeException(e);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		return modelName;
	}

	private boolean fileWrite(String path, String fileName, String content) {
		try {
			FileWriter myWriter = new FileWriter(path + "/" + fileName);
			myWriter.write(content);
			myWriter.close();			
			System.out.println("Successfully wrote to the file. " + fileName);
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return false;
	}

}
