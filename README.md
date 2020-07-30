# Spring Framework Impl
---
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)](https://GitHub.com/Naereen/StrapDown.js/graphs/commit-activity)

[![ForTheBadge built-with-love](http://ForTheBadge.com/images/badges/built-with-love.svg)](https://GitHub.com/Naereen/)

[![Ask Me Anything !](https://img.shields.io/badge/Ask%20me-anything-1abc9c.svg)](https://GitHub.com/Naereen/ama)
[![HitCount](http://hits.dwyl.io/Naereen/badges.svg)](http://hits.dwyl.io/Naereen/badges)

## IOC & DI

### Bean Container & Management

~~~java
@Component
@Controller
@Service
@Repository
~~~

* Store beans
	- `ConcurrentHashMap<Class<?>, Object>`


* Load beans

	- Extract class from given packages
		- Generate class instances
			* Input: the packages to scan
			* Output: the class instances set

			1. Get packages need to scan 
			2. Use ClassLoader to find the URI of the packages
			3. Scan all files under the packages. Use Java reflection to `Class.forName(className);`

			
	- For each extracted class
		- If the class doesn't have defined annotation, container will not manage it.
		- If the class has one, use Java reflection to new instance `clazz.getDeclaredConstructor().newInstance();`

		
* CRUD on beans
	- [Get] 
		- All instances in the container
		- All classes in the container
		- Given class, get instance
		- Given annotation, get class
		- Given super class, get sub classes

	- [Create]
		- Add a bean into container
	
	- [Delete]


### DI - Dependency Injection

~~~java
@Autowired

~~~

* Steps: 
	- get all classes in the container
  	- get all fields in this clazz
  	- check if this field has @Autowired
	- get the class type of this field
  	- get instance of this class
 	- use Java reflection to inject filed instance