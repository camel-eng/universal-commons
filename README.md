# universal-commons
This package is the basic package used in the "universal framework" series.

it provides resource management and data type conversion functions for numeric/date, that support system internationalization.

The languages ​​we currently support are en and ja,  
but you can increase the number of languages ​​supported by developing additional conversion classes.

<img src="https://camo.qiitausercontent.com/00f9cc65cdea735164a23edab49f10a1bf9cb56a/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f2d4a6176612d3030373339362e7376673f6c6f676f3d6a617661267374796c653d666f722d7468652d6261646765">

# overview
* Loading property files for supported languages.
* String and datetime conversion according to supported languages.  
  (Additional implementation is required for languages other than en and ja)
* String and numeric conversion according to supported languages.  
  (Additional implementation is required for languages other than en and ja)
* User information storage class.
  (please override and use)
* Container class using ThreadLocal.
* Case-insensitive map function.
* Data comparison function.
* File operation function.
* Wrapper class for BLOB/CLOB data.
* NULL class whose data type can be determined.
  (The above data comparison function evaluates to NULL)
* Debugger that allows you to use any debugging functions.

# for License
The source code is licensed MIT, please see "LICENSE" file.

Special Thanks [The MIT License](https://opensource.org/license/mit/)
