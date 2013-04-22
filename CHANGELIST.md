0.3
===

* Added support for use of upload code in the Color Extraction API methods.

0.2
===

* Changed types in net.jakobnielsen.imagga.crop_slice.bean.Region from java.lang.Long to java.lang.Integer.
* Introduced configuration of connection timeout and read timeout.
* Refactored the array implode method to ListTools.
* Introduced a new Resolution object. This is now implemented in the Smart Cropping method interface and in the smart
  cropping result.
* Added support for no resolutions given in the CropSliceAPIClient.smartCroppingByUrls method.
* Introduced two new variants of the smartCroppingsByUrls method to make use simpler.
