Imagga Java Kit
===============

**Current status : Support for Smart Cropping and Collage Slicing is added. Support for the Color Matching API will be added soon. 
The firste public release will be made available during March 2013.**


This toolkit is a Java wrapper around the Imagga (http://imagga.com) Rest APIs. It comes with no guarantees other than
it will do its best to serve you well.

Make sure to read the Imagga Technical Documentation before using this package : http://imagga.com/api/docs/index.html

Using the Imagga Smart Cropping and Collage Slicing API
-------------------------------------------------------
Please read the Imagga documentation for this API before continuing (http://imagga.com/api/docs/smart-cropping-collage-slicing.html)

### Smart Cropping API call

```java
// Initialize the client with the configuration you have received from Imagga.
CropSliceAPIClient client = new CropSliceAPIClient(myApiKey, myApiSecret, myApiEndpoint);

// Get smart croppings for the given url
List<SmartCropping> smartCroppings = client.smartCroppingByUrls(
                Arrays.asList("http://www.jakobnielsen.net/etc/images/cool-cartoon-291732.png"),
                Arrays.asList("50", "50", "150", "120"), true);
```

### Collage Slicing API call

```java
// Initialize the client with the configuration you have received from Imagga.
CropSliceAPIClient client = new CropSliceAPIClient(myApiKey, myApiSecret, myApiEndpoint);

List<DivisionRegion> divisionRegions = client.divisionRegionsByUrls(
                Arrays.asList("http://www.jakobnielsen.net/etc/images/cool-cartoon-291732.png"));
```

Use with Maven
--------------
This library haven't been released yet. So at the moment you will have to clone the source code and build the package 
manually with Maven 3. This documentation will be updated as soon as we have a release.

But this is what you should include in your project pom (Version will change of course)

```xml
<dependencies>
  <dependency>
    <groupId>net.jakobnielsen.imagga-java-kit</groupId>
    <artifactId>crop-slice</artifactId>
    <version>0.1-SNAPSHOT</version>
  </dependency>
</dependencies>
```
