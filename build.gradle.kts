//apply plugin: 'java'

plugins {
    java
    id("nebula.facet") version "8.2.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.glassfish.jersey:jersey-bom:3.0.2"))
    implementation ("org.glassfish.jersey.core:jersey-server")
    implementation ("org.glassfish.jersey.containers:jersey-container-jetty-http")
    implementation ("org.glassfish.jersey.containers:jersey-container-servlet-core")
    implementation ("org.glassfish.jersey.media:jersey-media-json-jackson")
    implementation ("org.glassfish.jersey.inject:jersey-hk2")

    implementation ("jakarta.xml.bind:jakarta.xml.bind-api:3.0.0")
    implementation ("com.sun.xml.bind:jaxb-impl:3.0.0")

    compileOnly("jakarta.servlet:jakarta.servlet-api:5.0.0")

    implementation(platform("org.eclipse.jetty:jetty-bom:11.0.6"))
    implementation("org.eclipse.jetty:jetty-server")
    implementation("org.eclipse.jetty:jetty-servlet")

    compileOnly ("org.slf4j:slf4j-api:1.8.0-beta4")
    implementation ("org.slf4j:slf4j-simple:1.8.0-beta4")

    testImplementation(platform("org.junit:junit-bom:5.8.0"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    testImplementation("org.apache.httpcomponents:httpclient:4.5.13")
//    testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
}



facets {
    // ugly syntax because the plugin is not so kotlin-compatible.
    // calls the method in NamedContainerProperOrder.groovy directly.
    create(
        "integTest",
        delegateClosureOf<nebula.plugin.responsible.TestFacetDefinition> {
            parentSourceSet = "test"
            includeInCheckLifecycle = false
        }
    )
}

tasks.withType<Test> {
    useJUnitPlatform()
}