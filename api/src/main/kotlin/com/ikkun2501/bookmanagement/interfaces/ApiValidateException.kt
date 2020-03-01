package com.ikkun2501.bookmanagement.interfaces

import javax.validation.ValidationException

class ApiValidateException(message: String) : ValidationException(message)
