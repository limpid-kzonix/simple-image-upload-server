# Deprecate: wanna to rewrite on MongoAsyncDriver (GridFs) and extend and separate (Image: with current logic, Video: with color processing frame-by-frame, gify-maker, pdf/djvu - two-way converting).  

# This is Play application with Java, JPA wrapper - Kundera (like Hibernate)

# File storage :


## Image storage : 

> resize, 

> crop,

> color processing;

### Image size: 

_(when you upload image we provide to different without expense in time. 
Async computation and caching data (15 min) after first call on API - [get image])_

S100( "100x100", 100, 100),
S200( "200x200", 200, 200 ),
S256( "200x200", 256, 256 ),
S300("300x300", 300, 300),
M400( "400x400", 400, 400),
M500( "500x500", 500, 500),
M512( "512x512", 512, 512),
M600( "600x600", 600, 600),
L720( "720x720", 720, 720),
L1024( "1024x1024", 1024, 1024),
TYPE_16X9("16:9", 1366, 768);
