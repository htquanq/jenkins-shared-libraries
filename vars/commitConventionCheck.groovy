#!/usr/bin/env groovy
import java.util.regex.Pattern

def call(Map args) {
  def pattern = Pattern.compile("(\\[|)\\b\\w+[A-Z][-]\\b\\d+[0-9](\\]|:)\\s+.*")
  commitMessage = args.commit
  println(commitMessage ==~ pattern)
//  commit_message = sh(returnStdout: true, script: "git log --pretty=\"%s\" -n1 | cat | head").trim()
  if ((commitMessage ==~ pattern) == false) {
    currentBuild.result = "FAILED"
  }

}

