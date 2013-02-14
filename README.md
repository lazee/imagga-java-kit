Imagga Java Kit
===============

**Current status : Support for Smart Cropping and Collage Slicing is added, as well as the Color Extraction API. 
The package will be released as soon as we have implemented the Color Search API**


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

Using the Imagga Color Extraction and Multi-Color Search API
------------------------------------------------------------

### Color Extraction API call

```java
// Initialize the client with the configuration you have received from Imagga.
ColorAPIClient client = new ColorAPIClient(myApiKey, myApiSecret, myApiEndpoint);

List<ColorResult> colorResults = client.colorsByUrls(Arrays.asList("http://www.jakobnielsen.net/etc/images/cool-cartoon-291732.png", 
"http://www.toondoo.com/public/l/a/z/lazee/toons/cool-cartoon-152229.png"));
```

### Multi-Color Search API

Support for this API is not implemented into the package yet. This will be done soon.


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
