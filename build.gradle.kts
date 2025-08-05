import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.spring") version "2.2.0"
    id("org.jetbrains.dokka") version "2.0.0"
    jacoco
    id("maven-publish")
    id("org.jreleaser") version "1.19.0"
    signing
}

signing {
    val signingKey = System.getenv("JRELEASER_GPG_SECRET_KEY")
    val signingPassword = System.getenv("JRELEASER_GPG_PASSPHRASE")
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "com.veryfi"
            artifactId = "veryfi-kotlin"
            version = "2.0.2"
            from(components["java"])
            pom {
                name.set("Veyfi Kotlin")
                description.set("Android kotlin module for communicating with the Veryfi OCR API")
                url.set("https://github.com/veryfi/veryfi-kotlin")
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("Veryfi")
                        name.set("Veryfi")
                        email.set("support@veryfi.com")
                    }
                }
                scm {
                    url.set("https://github.com/veryfi/veryfi-kotlin")
                }
            }
        }
    }
    repositories {
        maven {
            setUrl(layout.buildDirectory.dir("staging-deploy"))
        }
    }
}

jreleaser {
    release {
        github {
            enabled = true
            skipRelease = true
            skipTag = true
        }
    }
    project {
        name = "Veyfi Kotlin"
        version = "2.0.2"
        description = "Android kotlin module for communicating with the Veryfi OCR API"
        copyright = "Veryfi"
        java {
            groupId = "com.veryfi"
            artifactId = "veryfi-kotlin"
        }
    }
    deploy {
        maven {
            mavenCentral {
                create("sonatype") {
                    setActive("ALWAYS")
                    sign = false
                    url = "https://central.sonatype.com/api/v1/publisher"
                    stagingRepository(layout.buildDirectory.dir("staging-deploy").get().toString())
                    setAuthorization("Basic")
                }
            }
        }
    }
}

tasks.named("jreleaserDeploy") {
    dependsOn("publish")
}

repositories {
    mavenCentral()
}

jacoco {
    toolVersion = "0.8.13"
    reportsDirectory.set(layout.buildDirectory.dir("jacocoReports"))
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(false)
        csv.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.json:json:20250517")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.13.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "com.vaadin.external.google", module ="android-json")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}