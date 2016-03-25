Imagga Java Kit
===============

** This repository is no longer maintained as Imagga now provide their own documentation on how to use their API with Java **

This toolkit is a Java wrapper around the Imagga (http://imagga.com) Rest APIs. It comes with no guarantees other than
it will do its best to serve you well.

Make sure to read the Imagga Technical Documentation before using this package : http://imagga.com/api/docs/index.html

News
----

 * [Apr. 2013] Version 0.3 of imagga-java-kit released and deployed to official Maven repositories via Nexus. Support for upload code in the Color Extraction API methods.
 * [Mar. 2013] Version 0.2 of imagga-java-kit released and deployed to official Maven repositories via Nexus. This version supports using an upload code instead of an url for smart cropping.
 * [Feb. 2013] Version 0.1 of imagga-java-kit released and deployed to official Maven repositories via Nexus.
 

Using the Imagga Smart Cropping and Collage Slicing API
-------------------------------------------------------
Please read the Imagga documentation for this API before continuing (http://imagga.com/api/docs/smart-cropping-collage-slicing.html)

### Smart Cropping API Request By Urls

```java
// Initialize the client with the configuration you have received from Imagga.
CropSliceAPIClient client = new CropSliceAPIClient(new APIClientConfig(myApiKey, myApiSecret, myApiEndpoint));

// Get smart croppings for the given url
List<SmartCropping> smartCroppings = client.smartCroppingByUrls(
                Arrays.asList("http://www.jakobnielsen.net/etc/images/cool-cartoon-291732.png"),
                Arrays.asList("50", "50", "110", "100"), true);
```

### Smart Cropping API Request By Upload Code

```java
// Initialize the upload client with the configuration you have received from Imagga.
UploadClient uploadClient = new UploadClient(new APIClientConfig(myApiKey, myApiSecret, myApiEndpoint));

// Initialize the client with the configuration you have received from Imagga.
CropSliceAPIClient client = new CropSliceAPIClient(new APIClientConfig(myApiKey, myApiSecret, myApiEndpoint));

List<SmartCropping> lst = client.smartCroppingByUploadCode(
                        uploadClient.uploadForProcessing(new File("/tmp/myimage.png"),
                        true,
                        Arrays.asList(new Resolution(50, 50)),
                        true);
```


### Collage Slicing API Request

```java
// Initialize the client with the configuration you have received from Imagga.
CropSliceAPIClient client = new CropSliceAPIClient(new APIClientConfig(myApiKey, myApiSecret, myApiEndpoint));

List<DivisionRegion> divisionRegions = client.divisionRegionsByUrls(
                Arrays.asList("http://www.jakobnielsen.net/etc/images/cool-cartoon-291732.png"));
```

Using the Imagga Color Extraction and Multi-Color Search API
------------------------------------------------------------

### Color Extraction API Request By Urls

```java
// Initialize the client with the configuration you have received from Imagga.
ColorAPIClient client = new ColorAPIClient(new APIClientConfig(myApiKey, myApiSecret, myApiEndpoint));

ColorsByUrlsRequest request = new ColorsByUrlsRequest();

request.setUrlsToProcess(Arrays.asList(
    new IndexableImage("http://www.jakobnielsen.net/etc/images/cool-cartoon-291732.png", 100),
    new IndexableImage("http://www.toondoo.com/public/l/a/z/lazee/toons/cool-cartoon-152229.png", 101)));

List<ColorResult> colorResults = client.colorsByUrls(request);
```

### Color Extraction API Request By Upload Code

```java
// Initialize the upload client with the configuration you have received from Imagga.
UploadClient uploadClient = new UploadClient(new APIClientConfig(myApiKey, myApiSecret, myApiEndpoint));

// Initialize the client with the configuration you have received from Imagga.
ColorAPIClient client = new ColorAPIClient(new APIClientConfig(myApiKey, myApiSecret, myApiEndpoint));

ColorsByUploadCodeRequest request = new ColorsByUploadCodeRequest();
        request.setUploadCode(uploadClient.uploadForProcessing(new File("/tmp/myimage.png"));
        request.setImageId(2);
        request.setDeleteAfterwords(true);
        request.setExtractObjectColors(true);
        request.setExtractOverallColors(true);

List<ColorResult> lst = client.colorsByUploadCode(request);
```

### Multi-Color Search API Request

```java
// Initialize the client with the configuration you have received from Imagga.
ColorAPIClient client = new ColorAPIClient(new APIClientConfig(myApiKey, myApiSecret, myApiEndpoint));

RankSimilarColorRequest request = new RankSimilarColorRequest();

request.setColorIndexType(ColorIndexType.OVERALL);
request.setColorVector(Arrays.asList(
    new Color(60, 255, 0, 0),
    new Color(40, 0, 255, 0)
));
request.setCount(10);
request.setDist(12);

List<RankSimilarity> rankSimilarities = client.rankSimilarColor(request);
```

Use with Maven
--------------

Include this in your project pom

```xml
<dependencies>
  <dependency>
    <groupId>net.jakobnielsen</groupId>
    <artifactId>imagga-java-kit</artifactId>
    <version>0.3</version>
  </dependency>
</dependencies>
```
