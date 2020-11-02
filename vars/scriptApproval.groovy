#!/usr/bin/env groovy
import org.jenkinsci.plugins.scriptsecurity.scripts.*
import org.jenkinsci.plugins.scriptsecurity.sandbox.whitelists.*
import com.cloudbees.plugins.credentials.*
import groovy.json.JsonOutput

def call(){
  def scriptApproval = ScriptApproval.get()
  List<String> pendingScripts = [] as String[]
  List<String> pendingSignatures = [] as String[]

  // Approve signature
  for(ScriptApproval pending: scriptApproval.getPendingSignatures()){
    pendingSignatures.add(pending.signature)
    // scriptApproval.approveSignature(pending.signature)
  }

  // Approve scripts
  for(ScriptApproval pending: scriptApproval.getPendingScripts()){
    pendingScripts.add(pending.script)
    // scriptApproval.approveScript(pending.getHash())
  }

  send("",pendingScripts)
  send("",pendingSignatures)
}

def send(String channel = "", List<String> pending = []) {
  if(!pending.isEmpty()) {
    def json = JsonOutput.toJson(
      [
        username: "Jenkins",
        icon_url: "https://jenkins.io/images/logos/jenkins/jenkins.png",
        attachments: [
          [
            color: "#764FA5",
            blocks : [
              [
                type: "section",
                text: [
                  type: "mrkdwn",
                  text: "Approved new pending scripts/signatures:\n\t```${pending.join('\n')}```"
                ]
              ]
            ]
          ]
        ]
      ]
    ).toString()
    println(json)

    try {
      def slack_url = ""
      def credentials = CredentialsProvider.lookupCredentials(com.cloudbees.plugins.credentials.Credentials.class, Jenkins.instance)
      for(cred in credentials) {
        if(cred.id == "HOOK_URL") {
          slack_url = cred.secret
        }
      }
      def connection = new URL(slack_url.toString()).openConnection()
      connection.setRequestMethod('POST')
      connection.setRequestProperty('Content-Type', 'application/json')
      connection.setDoOutput(true)
      def output = new OutputStreamWriter(connection.outputStream)
      output.write(json)
      output.close()
      if ( connection.responseCode != 200 ) {
        error("Failed sending to slack")
      }
    } catch(err) {
      println("${err.toString()}. Can't send message to slack.")
    }
  }
}
