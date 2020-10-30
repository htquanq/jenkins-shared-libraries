#!/usr/bin/env groovy
import java.util.regex.Pattern

def call() {
  def pattern = Pattern.compile("(\\[|)\\b\\w+[A-Z][-]\\b\\d+[0-9](\\]|:)\\s+.*")
  commitMessage = sh(returnStdout: true, script: "git log --pretty=%s -n1 | cat | head").trim()
  if ((commitMessage ==~ pattern) == false) {
    currentBuild.result = "ABORTED"
    error("Commit message didnt match the convention.")
  }

}
