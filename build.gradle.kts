import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.1"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.0"
	kotlin("plugin.spring") version "1.6.0"
	id("org.jetbrains.dokka") version "1.6.0"
	jacoco
	id("maven-publish")
}

afterEvaluate {
	publishing {
		repositories {
			maven {
				name = "OSSRH";
				setUrl("https://s01.oss.sonatype.org/content/repositories/releases")
				credentials {
					username = System.getenv("MAVEN_USERNAME")
					password = System.getenv("MAVEN_PASSWORD")
				}
			}
		}
		publications.withType<MavenPublication> {

			groupId = "com.veryfi"
			artifactId = "veryfi-kotlin"
			version = "1.0.0"
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
						id.set("androide0917")
						name.set("Yuber Garcia")
						email.set("yubergarcia@veryfi.com")
					}
				}
				scm {
					url.set("https://github.com/veryfi/veryfi-kotlin")
				}
			}
		}
	}
}

repositories {
	mavenCentral()
}

jacoco {
	toolVersion = "0.8.7"
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
	implementation("org.json:json:20180813")
	testImplementation("junit:junit:4.13.2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
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
