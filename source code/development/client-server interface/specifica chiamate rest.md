### Specifica dei json per le chiamate al server

#AppInfo

**url**: `/appinfo`
**chiamata**: `GET`
**autenticazione**: `NO`
**restituisce**:
```
{
	"description"  : String,
	"supportEmail" : String,
	"websiteURL"   : String,
	"discoveryUUID": String
}
```


#Login

**url**: `/login`
**chiamata**: `POST`
**autenticazione**: `NO`
**body**:
```
{
  "email"    : String,
  "password" : String
}
```
**restituisce**:
```
{
	"token"    : String
}
```

#Logout
**url**: `/logout`
**chiamata**: `GET`
**autenticazione**: `SI`


#PathsResults

**url**: `/pathsresults`
**chiamata**: `GET`
**autenticazione**: `SI`
**restituisce**:
```
{
	"da definire"
}
```


#Path

**url**: `/path/{id}`
**chiamata**: `GET`
**autenticazione**: `NO`
**restituisce**:
```
{
	"da definire"
}
```

#Buildings
**url**: `/buildings`
**chiamata**: `POST`
**autenticazione**: `NO`
**body**:
```
{
	"latitude"  : Double,
	"longitude" : Double,
	"maxResults": Int,
}
```
**restituisce**:
```
{
	"da definire"
}
```
