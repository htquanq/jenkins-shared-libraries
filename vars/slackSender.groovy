#!/usr/bin/env groovy
import com.cloudbees.plugins.credentials.*

def call() {
  def credentials = CredentialsProvider.lookupCredentials(com.cloudbees.plugins.credentials.Credentials.class, Jenkins.instance)

  for(cred in credentials) {
    println(cred.id)
    println(cred.secret)
  }
}
