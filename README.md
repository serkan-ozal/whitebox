# WhiteBox
Public version of JDK 8+ internal WhiteBox API

1. What is WhiteBox?
==============
**WhiteBox** is one of the not so well-known tools of the HotSpot VM is its WhiteBox testing API. Introduced in Java 7 it has been significantly improved and extended in Java 8 and 9. It can be used to query or change HotSpot internals which are not otherwise exposed to Java-land. While its features make it an indispensable tool for writing good HotSpot regression tests, it can also be used for experiments or for the mere fun of peeking into the VM. (See https://wiki.openjdk.java.net/display/HotSpot/The+WhiteBox+testing+API for more details)

In this public version, JDK's internal WhiteBox API is able to accessed and used easily without any extra effort through a simple API (`sun.hotspot.WhiteBoxProvider`).

2. Installation
==============

In your `pom.xml`, you must add repository and dependency for **Whitebox**. 

``` xml
...
<properties>
    ...
    <whitebox.version>1.8</whitebox.version>
    ...
</properties>
...
<dependencies>
    ...
	<dependency>
		<groupId>sun.hotspot</groupId>
		<artifactId>whitebox</artifactId>
		<version>${whitebox.version}</version>
	</dependency>
	...
</dependencies>
...
<repositories>
	...
	<repository>
		<id>serkanozal-maven-repository</id>
		<url>https://github.com/serkan-ozal/maven-repository/raw/master/</url>
	</repository>
	...
</repositories>
...
```

3. Usage
==============
Just
``` java
WhiteBox wb = WhiteBoxProvider.getWhiteBox();
``` 
is enough. Now you have `WhiteBox` instance and free to do some crazy things.

See 
* https://wiki.openjdk.java.net/display/HotSpot/The+WhiteBox+testing+API 
* http://hg.openjdk.java.net/jdk8u/jdk8u-dev/hotspot/file/tip/test/testlibrary/whitebox/sun/hotspot/WhiteBox.java
* http://jpbempel.blogspot.com.tr/2015/07/whitebox-api.html

for more details.

Here is an example to show capabilities of WhiteBox API:
``` java
Object obj = new Object();
System.out.println("Is Object in old gen: " + wb.isObjectInOldGen(obj));
        
// Age bit is represented with 4 bit, so it can be 15 at most.
// This means that an object can live in young generation during 15 minor GC.
// So 16 minor GC guarantees that object is promoted to old generation.
for (int i = 0; i < 16; i++) {
    System.out.println("Triggering young gc for " + (i + 1) + ". time ...");
    wb.youngGC();
}
        
System.out.println("Is Object in old gen: " + wb.isObjectInOldGen(obj));
```
