package com.bazaarvoice.jolt.helper;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;

public class MainClass {

	public static void main(String[] args) {
		try {
			Git
			   .open(new File("/Users/raraj/Documents/rspace/projects/freshservice/source/jolt-try/jolt-specs/.git"))
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
