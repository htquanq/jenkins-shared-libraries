def send(String channel = "", String pending = "") {
  def credentials = CredentialsProvider.lookupCredentials(com.cloudbees.plugins.credentials.Credentials.class, Jenkins.instance)
  for(cred in credentials) {
    if(cred.id == "HOOK_URL") {
      slack_url = cred.secret
    }
  }

  def json = JsonOutput.toJson(
    [
      "username": "Jenkins",
      "icon_url": "https://jenkins.io/images/logos/jenkins/jenkins.png",
      "attachments": [
        [
          "color": "#764FA5",
          "blocks" : [
            [
              "type": "section",
              "text": [
                "type": "mrkdwn",
                "text": "Found new pending approval scripts/signatures: ${pending}"
              ]
            ]
          ]
        ]
      ]
    ]
  )

  try {
    sh "curl -X POST -H 'Content-type: application/json' --data '${json}' ${slack_url} --max-time 5"
  } catch(err) {
    println("${err} Couldn't send slack message. Moving on!")
  }
}
