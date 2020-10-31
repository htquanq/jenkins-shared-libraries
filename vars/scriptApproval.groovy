#!/usr/bin/env groovy
import org.jenkinsci.plugins.scriptsecurity.scripts.*
import org.jenkinsci.plugins.scriptsecurity.sandbox.whitelists.*;

// create new script approval object
def call(){
  def scriptApproval = ScriptApproval.get()
  //for(ScriptApproval.ApprovedWhitelist approved : scriptApproval.getApprovedSignatures()) {
  //  println(approved.signature)
  //}

  for(ScriptApproval pending: scriptApproval.getPendingSignatures()){
    println("Pending: " + pending.signature)
    println("Approving: " + pending.signature)
    scriptApproval.approveScript(pending.getHash())
  }
}
