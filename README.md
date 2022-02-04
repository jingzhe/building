# link heroku: heroku git:remote -a hidden-basin-27129

# deploy to Heroku:git push heroku master

keytool -genkeypair -alias building_auth -keyalg RSA -keypass testpass -storepass storepass -validity 1000 -deststoretype jks -keystore building.jks

keytool -exportcert -keystore building.jks -alias building_auth -rfc -file building_pub.crt