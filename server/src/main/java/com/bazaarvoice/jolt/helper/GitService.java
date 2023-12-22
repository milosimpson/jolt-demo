package com.bazaarvoice.jolt.helper;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.lib.RepositoryCache.FileKey;
import org.eclipse.jgit.util.FS;

public class GitService {

	private String basePath = null;
	
	
	public GitService(String basePath) {
		this.basePath = basePath;
	}
	
	public Repository getHostedRepository(String name) throws IOException {
		File repoDir = new File(getTenantHostedBaseDir(), name);
		FileKey key = FileKey.exact(repoDir, FS.DETECTED);
		Repository repo = RepositoryCache.open(key, true);
		return repo;
	}
	public File getTenantHostedBaseDir() {
		return new File(basePath);
	}
	
	public void check(String repositoryPath) {
		try {
			Git
			   .open(new File(repositoryPath))
			   .reset()
			   .setMode(ResetType.HARD)
			   .call();
		} catch (CheckoutConflictException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
