#!/usr/bin/env groovy
import org.jenkinsci.plugins.scriptsecurity.scripts.*
import org.jenkinsci.plugins.scriptsecurity.sandbox.whitelists.*;

// create new script approval object
def call(){
  def scriptApproval = ScriptApproval.get()
  //for(ScriptApproval.ApprovedWhitelist approved : scriptApproval.getApprovedSignatures()) {
  //  println(approved.signature)
  //}
  // Approve signature
  for(ScriptApproval pending: scriptApproval.getPendingSignatures()){
    println("Pending signature: " + pending.signature)
    println("Approving signature: " + pending.signature)
    scriptApproval.approveSignature(pending.signature)
  }

  // Approve scripts
  for(ScriptApproval pending: scriptApproval.getPendingScripts()){
    println("Pending script: " + pending.script)
    println("Approving script: "+ pending.script)
    scriptApproval.approveScript(pending.getHash())
  }
}
