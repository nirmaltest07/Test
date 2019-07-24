#Browserstack setup
1. Create an browser stack account
2. Open the terminal and navigate to the project folder
3. Use below command to push the apk/ipa file to the browserstack cloud

Android:
curl -u "username:accesskey" \
  -X POST "https://api-cloud.browserstack.com/app-automate/upload" \
  -F "file=@androidautomation/android/resources/EriBank.apk"  
  
iOS:
curl -u "username:accesskey" \
  -X POST "https://api-cloud.browserstack.com/app-automate/upload" \
  -F "file=@iosautomation/ios/resources/BStackSampleApp.ipa"  

3. Go to runner classe and update the app id received from the above command in the capabilities


Latest npm:
npm install npm@latest -g
tern ide plugin
angular plugin

