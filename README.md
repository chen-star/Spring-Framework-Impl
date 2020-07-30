
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