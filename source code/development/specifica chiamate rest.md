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
[{
	"pathId" : Int,
	"pathName": String,
	"buildingName": String,
	"startTime" : String,
	"endTime" : String,
	"proofResults" : [
		{
			"proofId" : Int,
			"startTime" : String,
			"endTime" : String,
			"score" : Int
		}, { ... }, { ... } ]
},
{...},
{...}]
```

**url**: `/pathsresults`
**chiamata**: `POST`
**autenticazione**: `SI`
**body:**
```
{
	"pathId" : Int,
	"startTime" : String,
	"endTime" : String,
   "score" : Int,
	"proofResults" : [
		{
			"proofId" : Int,
			"startTime" : String,
			"endTime" : String,
			"score" : Int
		}, { ... }, { ... } ]
}
```


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
	"maxDistance": Double,
   "maxResults": Int
}
```
**NOTA: è sufficiente impostare un solo valore tra `maxResults` e `maxDistance`**
**restituisce**:
```
{
	"name" : String,
	"description" : String,
	"otherInfo" : String,
	"address" : String,
   "paths": [
      {
         "id": Int,
         "title" : String,
         "description": String,
         "target" : String,
         "address" : String,
         "position" : Int
      },
      {...}
   ]
}
```

# Registration Data Check
**url**: `/registrationFieldCheck`
**chiamata**: `POST`
**autenticazione**: `NO`
**body**:
```
{
	"username" : String,
	"email"    : String,
	"password" : String
}
```
**restituisce**:
```
{
	"username": {
		"isValid" : Bool,
		"userMessage": String?
	},
	"password": {
	},
	"email": {
	}
}
```

# User Data
**url**: `/userData`
**chiamata**: `POST`
**autenticazione**: `SI`
**body**:
```
{
	"username": String?
	"password": String?
}
```
**restituisce**:
```
{
	"saved": Bool
	"userMessage": String?
}
```

**url**: `/userData`
**chiamata**: `GET`
**autenticazione**: `SI`
**restituisce**:
```
{
	"email": String
	"username": String
}
```

#Registration
**url**: `/newUser`
**chiamata**: `POST`
**autenticazione**: `NO`
**body**:
```
{
	"username" : String,
	"email"    : String,
	"password" : String
}
```
**restituisce**:
```
{
	"userID" : String?,
	"token"  : String?,
	"errorCode": Int?,
	"userMessage": String?
}
```

#Field Validation
**url**: `/validateFields`
**chiamata**: `POST`
**autenticazione**: `NO`
**body**:
```
{
   "customToken": Any?,
	"username" : String,
	"email"    : String,
	"password" : String
}
```
_qualcuno dei campi può essere omesso, ovviamente la validazione ritornerà falso_
**restituisce**:
```
{
	"customToken": Any,
   "email": {
      "isValid": Bool,
      "reason" : String?,
      "userMessage": String?
   },
   "username": {
      "isValid": Bool,
      "reason" : String?,
      "userMessage": String?
   },
   "password": {
      "isValid": Bool,
      "reason" : String?,
      "userMessage": String?
   }
}
```
_il customToken è un campo opzionale che il client può usare per distinguere tra alcune chiamate consecutive nel caso in cui parta una seconda chiamata prima che torni la prima_
