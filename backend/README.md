# Backend

The backend is the main component of this project. It consists of the following main components:

- Machine Learning: The model training and predictions are done in this component by utilizing the Apache Spark framework. The LogisticRegression is used as a Machine Learning algorithm. During training the Model is evaluated by using many various parameters of the learning algorithm and at the end only the best combination is picked for the final model. The training dataset is saved under /resources/data and originates from (TODO)
- Repository: This is a gateway between Amazon DynamoDB and this backend
- Service: Business Logic of this component: CRUD operations of used entities (Patient and HeartData).
- Notification: Connect to the Firebase Services to push notifications to the end devices - Android apps.

### Getting Started
1. Install Apache Spark and set the spar.home directory in application.properties
2. Create tables 'Patient' and 'HeartData' in DynamoDB in AWS
3. Put the correct DynamoDB endpoint to application.properties
4. Create a user at AWS with IAM allowing it to access your DynamoDB and put the obtained access and secret key to application.properties
5. Create a project in Firebase Console and put the obtained firebase api key to application.properties