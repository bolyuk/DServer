# DServer 0.3 (05/03/2020)
Important! Libraries have changed along with some features ([old documentation](https://telegra.ph/DBolk-02-documentation-02-09))

Dbolk was created to install on an old android device and turn it into a home server that can work with google firebase and telegram bots at the moment.
(have some autonomy issues)

# Documentation

a section describing the available classes and functions that are available out of the box (you can upload your own lua libraries - see code examples)

## DBolk
imported as **dbolk**

DBolk is the only Java class that is pre-imported. The most important and useful class

````lua code
if dbolk:compatibility(3) then                    
    --check engine version
 dbolk:init()                                     
    --clear dbolk global libraries
 dbolk:loadG("org.bolyuk.DFile","file")           
    --load DFile library as file in global (the effect will be from a new run of lua the code)
 dbolk:load("org.bolyuk.DFile","file")            
    --load library in local
 dbolk:load("org.bolyuk.DVars","vars")            
 dbolk:install()                                  
    --install libraries
 dbolk:installG()                                 
    --install libraries in global
 dbolk:load(file:read(vars:getS("my-lua-lib"))    
    --load lua library from a file
 dbolk:installG()                                 
 dbolk:run(file:read(vars:getS("my-startup-code")))  
    --run lua code from file (new library will work)
    --it is not recommended to call another code after calling this function
else
 print("code is not compatible with this version!")

````

## DFile
This class allows you to access files on the device and interact with them. needed to load and unload external lua files. If access to files is denied, DVars can become an analogue

article in progress...
