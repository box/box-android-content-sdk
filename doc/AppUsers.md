Developer's Edition (App Users)
==============

Authentication
---------------------
When using Developer's Edition (App Users), developers must implement AuthenticationRefreshProvider. It is recommended this implementation be set in the first line of the BoxAuthentication class in order to handle all sessions.   
```java
private final refreshProvider = new BoxAuthentication.AuthenticationRefreshProvider() {
@Override
public BoxAuthentication.BoxAuthenticationInfo refreshAuthenticationInfo(BoxAuthentication.BoxAuthenticationInfo info) throws BoxException {
// Do things to retrieve updated access token from the previous info. 
String refreshedAccessToken = MyServer.getNewAccessToken(info);
BoxAuthenticationInfo refreshedInfo = new BoxAuthenticationInfo(info.toJsonObject());
refreshedInfo.setAccessToken(refreshedAccessToken);
return refreshedInfo;
}

@Override
public boolean launchAuthUi(String userId, BoxSession session) {
// return true if developer wishes to launch their own activity to interact with user for login.
// Activity should call BoxAuthentication. BoxAuthentication.getInstance().onAuthenticated() or onAuthenticationFailure() as appropriate.
// Make sure to use an application context here when starting your activity to avoid memory leaks.

return true;
}
};
BoxAuthentication.getInstance().setRefreshProvider(refreshProvider);

```

Once set, sessions can be used normally. The first time launching your auth UI provided from the launchAuthUi method. 
When using App Users with our other SDKs, currently you MUST use this approach currently. 



```java

BoxSession session = new BoxSession(context);
session.authenticate();
```

Single-Session 
------------------------
You can also manage sessions without changing BoxAuthentication by using the following session constructor.

```java
BoxAuthentication.BoxAuthenticationInfo info = new BoxAuthentication.BoxAuthenticationInfo();
// Populate info with as much information known as possible. If the access token is not provided the refresh provider implementation's launchAuthUi method will be invoked.
info.setAccessToken("the first access token");

BoxSession session = new BoxSession(context, info, REFRESH_PROVIDER_IMPL);
session.authenticate();
```

Log all users out.

```java
BoxAuthentication.getInstance().logoutAllUsers(context);
```

Security
------------------------
The SDK stores authentication information in shared preferences by default. It is recommended that you create your own implementation of `BoxAuthentication.AuthStorage` to provide additional security as needed. You can set the authentication storage as follows:
```java
BoxAuthentication.getInstance().setAuthStorage(new CustomAuthStorage());
```
