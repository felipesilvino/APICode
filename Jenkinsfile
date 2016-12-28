node ('master'){
  stage 'Pull'
  if (env.BRANCH_NAME.startsWith('PR-')) {
    checkout scm
  } else {
    checkout([
        $class: 'GitSCM',
        branches: [[name: "*/${env.BRANCH_NAME}"]],
        userRemoteConfigs: [[url: 'https://github.com/weisebrazil/APICode.git']],
        extensions: [[$class: 'LocalBranch', localBranch: "${env.BRANCH_NAME}"]]],
      )
  }

  stage 'Build'
  String gradle = tool 'Gradle 2.14'
  sh "${gradle}/bin/gradle clean assemble check --no-daemon --stacktrace"
}
