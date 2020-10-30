#!/usr/bin/env groovy
import java.util.regex.Pattern

def call(){
  def pattern = Pattern.compile("^((feature|hotfix)\\/\\b\\w+[A-Z][-]\\b\\d+.*|(develop|master|release).*)\$")
  branchName = env.BRANCH_NAME
  println(assert branchName ==~ pattern)
  if ((branchName ==~ pattern) == false) {
    currentBuild.result = "ABORTED"
    error("Branch name didnt match the convention.")
  }
}
