// FILENAME:     util.js

// DESCRIPTION:  General JavaScript utility functions

// CONTENTS:     This file contains general utility functions for use in JavaScript

// AUTHORS:      Benjamin Lindahl (Ben@AIT-INC.com)
//               Gurney Thompson (Gurney@AIT-INC.com)

// COMPANY:      Applied Information Technologies, Inc. (http://www.ait-inc.com/)

// PROJECT:      This file is part of the JS-Lib (JavaScript Library) portion of the AITWorks project

// VERSION:      JS-Lib 0.0.1

// CO-REQUISITE INCLUDES/FILES: None

// SPECIAL USAGE INSTRUCTIONS: None

// FUNCTION CATEGORIES:
//   -- Debugging Functions:        Allow you to have alert messages appear conditional upon whether debugging is turned on or off
//   -- General Functions:          Generally useful functions when programming in JavaScript, to give you more flexibility with the language
//   -- Array Functions:            Check and manipulate arrays
//   -- Logic Functions:            Arithmetic and binary logic
//   -- String Functions:           Check and manipulate strings
//   -- User Interface Functions:   Alert, etc functions for user interface
//   -- Data Validation Functions:  Data validation that does not belong explicitly to any other util libraries
//   -- Misc Functions:             Miscellaneous, do not belong in any other util libraries

// PARTICULARLY USEFUL FUNCTIONS:
//   General Functions:
//   -- clone:                      Perform a deep copy on an object, allowing you to use a copy of an object instead of the reference to it
//   -- nvl:                        Return the first argument if it is not null or an empty string, the second if it is
//
//   Array Functions:
//   -- insertIntoArray:            Insert a generic object into an array at the specified location
//   -- arrayContains:              Check if an array contains a particular object
//   -- arrayContains:              Check if an array contains a particular string, without regard to case
//
//   Logic Functions:
//   -- allTrue:                    Check whether all boolean parameters passed in are true
//   -- allFalse:                   Check whether all boolean parameters passed in are false
//   -- xor:                        Check whether exactly one boolean parameter passed in is true
//   -- between:                    Check whether a value is between given lower and upper bounds
//   -- min:                        Get the minimum of any number of values
//   -- max:                        Get the maximum of any number of values
//
//   String Functions:
//   -- stringEmpty:                Check whether a string is empty
//   -- validStringLength:          Check whether a string is between the min and max length
//   -- rpad:                       Pad the right side of the string with the provided character until it is a certain length long
//   -- lpad:                       Pad the left side of the string with the provided character until it is a certain length long
//   -- rtrim:                      Trim the provided character off the right side of the string
//   -- ltrim:                      Trim the provided character off the left side of the string
//
//   Misc Functions:
//   -- printDoc:                   Print the document (call the browser's print functionality, to send the document to a printer)


//////////////////////
// Global Variables //
//////////////////////

// Debug boolean
var debugBool = false;

/////////////////////////
// Debugging Functions //
/////////////////////////

// Debug alert
// Alert if debugBool is true
// *U*
function debugAlert(msg)
{
    condAlert(msg, debugBool);
}

// Set Debug
// Set debugBool to the boolean passed in
// *U*
function setDebug(boolIn)
{
    debugBool = true;
}

// Debug On
// Turn debugging on
// *U*
function debugOn()
{
    setDebug(true);
}

// Debug Off
// Turn debugging off
// *U*
function debugOff()
{
    setDebug(false);
}

// Function Name
// Get the name of the function passed in
// *U*
function functionName(funcIn)
{
    var retName = funcIn.toString().match(/function (\w*)/)[1];
    if(retName == null || retName.length == 0)
        return "Anonymous";
    return retName;
}

// Stack Trace
// Trace the stack from the calling function all the way back to the main page
// *U*
function stacktrace()
{
    var currRecursion = 0;
    var maxRecursion = 30;
    var retStack = "";

    for (var currFunc = arguments.caller; currFunc != null; currFunc = currFunc.caller)
    {
        retStack += "->" + functionName(currFunc.arguments.callee) + "\n";
        if (currFunc.arguments.caller == currFunc)
        {
            if(currRecursion < maxRecursion)
            {
                currRecursion++;
            } else {
                retStack += "*";
                break;
            }
        } else {
            currRecursion = 0;
        }
    }
}

///////////////////////
// General Functions //
///////////////////////

// Clone
// Clone the object, and any properties that are objects, etc, up to the depth maxDepth (1 means just clone this object, not any of its sub-objects)
// *U*
function clone (objIn, maxDepth, depth)
{
    var beenHit = false;
    var stopPrint = false;

    if(arguments.length < 2)
        maxDepth = 1;

    if(arguments.length < 3)
        depth = 0;
    
    if(!objIn || depth >= maxDepth)
        return objIn;

    if(typeof objIn != 'object')
        return objIn;

    var objClone = new objIn.constructor();

    for (var property in objIn)
    {
/*
        var constructorString = "";
        if(typeof objIn[property] == 'object')
            if(!objIn[property])
                constructorString = "\nNULL";
            else
            {
                constructorString = "\nItem: " + objIn[property] + "\nConstructor: " + objIn[property].constructor.toString();
                beenHit = true;
            }
        if(!stopPrint)
            alert("Property: " + property + "\nType: " + typeof objIn[property] + constructorString);
        if(beenHit)
            stopPrint = true;
*/

        objClone[property] = clone(objIn[property], maxDepth, depth + 1);
    }

    return objClone;
}

// Null Value
// Returns the first argument if it is not null or an empty string, the second if the first is null or an empty string
function nvl(valIn, defaultIn)
{
    if(valIn == null)
        return defaultIn;

    if(typeof valIn == "string")
        if(valIn == "")
            return defaultIn;

    return valIn;
}



/////////////////////
// Array Functions //
/////////////////////

// Make Array
// Creates an array of size n and initializes all elements to 0
function makeArray(n) {
    var newArray = new Array();

    for (var i = 1; i <= n; i++) {
        newArray[i] = 0;
    }
 
    return newArray;
}

// Is Array
// Return a boolean value telling whether the argument is an Array object. 
function isArray(argIn)
{
    if(typeof argIn == "object")
        if(typeof argIn.constructor != "undefined")
        {
            var criterion = arguments[0].constructor.toString().match(/array/i);
            return (criterion != null);
        }

    return false;
}

// Array of Property
// Return an array of all of the values of one particular property of each array element
// *U*
function arrayOfProperty(arrayIn, propertyIn)
{
    var retArray = new Array(arrayIn.length);

    for(var i = 0; i < arrayIn.length; i++)
        retArray[i] = arrayIn[i][propertyIn];

    return retArray;
}

// Array Contains (Case Insensitive Version)
// Return a boolean value telling you whether valueIn is contained in arrayIn, without respect to case
// *U*
function arrayContainsInsensitive(arrayIn, valueIn)
{
    valueIn = valueIn.toUpperCase()

    for(var i = 0; i < arrayIn.length; i++)
    {
        if(arrayIn[i].toUpperCase() == valueIn)
            return true;
    }

    return false;
}

// Array Contains
// Return a boolean value telling you whether valueIn is contained in arrayIn
// *U*
function arrayContains(arrayIn, valueIn)
{
    for(var i = 0; i < arrayIn.length; i++)
    {
        if(arrayIn[i] == valueIn)
            return true;
    }

    return false;
}

// Insert Into Array
// Insert the desired element into the array at the desired location
// *U*
function insertIntoArray(arrayIn, valueIn, pos)
{
    arrayIn.length++;

    if(arguments.length < 3)
        pos = arrayIn.length - 1;

    if(pos >= arrayIn.length)
        pos = arrayIn.length - 1;

    for(var i = arrayIn.length - 1; i > pos; i--)
    {
        arrayIn[i] = clone(arrayIn[i - 1]);
    }

    arrayIn[pos] = valueIn;

    return arrayIn;
}


/////////////////////
// Logic Functions //
/////////////////////

// All True
// Returns whether all the arguments are true (works on an array of booleans, or as many boolean arguments as you pass in)
function allTrue(args)
{
    if(arguments.length == 1)
    {
        for(var i = 0; i < args.length; i++)
        {
            if(!arguments[i])
                return false;
        }

        return true;
    } else {
        var argv = arguments;

        return allTrue(argv);
    }
}

// All False
// Returns whether all the arguments are false (works on an array of booleans, or as many boolean arguments as you pass in)
// *U*
function allFalse(args)
{
    if(arguments.length == 1)
    {
        for(var i = 0; i < args.length; i++)
        {
            if(arguments[i])
                return false;
        }

        return true;
    } else {
        var argv = arguments;

        return allFalse(argv);
    }
}

// Count true booleans
// Returns the number of booleans passed in that are true (works on an array of booleans, or as many boolean arguments as you pass in)
// *U*
function numTrue(argv)
{
    if(arguments.length == 1)
    {
        var argc = argv.length;

        var trueCount = 0;

        for(var i = 0; i < argc; i++)
        {
            if(argv[i]) trueCount++;
        }

        return trueCount;
    } else {
        return numTrue(arguments);
    }
}

// Exclusive or
// Returns true only if exactly one of the booleans is true (works on an array of booleans, or as many boolean arguments as you pass in)
// *U*
function xor()
{
    if(arguments.length == 1)
    {
        return (numTrue(arguments[0]) == 1);
    } else {
        // To Do: Not sure if this will cause problems, since arguments isn't a typical array, but since it has all the same properties defined, it should work
        return xor(arguments);
    }
}

// Between
// Returns whether the first argument is between the second and third arguments
function between(val, lower, upper)
{
    if(isNaN(val) && (typeof val == "string" || typeof val == "number"))
        return false;

    if(val <= upper && val >= lower)
        return true;
    else
        return false;
}

// Min
// Return the minimum of two values passed in
function min()
{
    if(arguments.length == 0)
        return null;

    var thisArray;

    if(arguments.length == 1)
    {
        if(isArray(arguments[0]))
            thisArray = arguments[0];
        else
            return arguments[0];
    } else
        thisArray = arguments;

    var minVal = thisArray[0];

    for(var i = 1; i < thisArray.length; i++)
    {
        if(thisArray[i] < minVal)
            minVal = thisArray[0];
    }

    return minVal;
}

// Max
// Return the maximum of the values passed in
function max(val1, val2)
{
    if(arguments.length == 0)
        return null;

    var thisArray;

    if(arguments.length == 1)
    {
        if(isArray(arguments[0]))
            thisArray = arguments[0];
        else
            return arguments[0];
    } else
        thisArray = arguments;

    var maxVal = thisArray[0];

    for(var i = 1; i < thisArray.length; i++)
    {
        if(thisArray[i] > maxVal)
            maxVal = thisArray[0];
    }

    return maxVal;
}


//////////////////////
// String Functions //
//////////////////////

// String is empty
// Returns true if the string passed in is empty, and alerts error message with msg if msg is passed in
function stringEmpty(stringIn, msg) 
{
    var alertBool = false;
    if(arguments.length > 1)
        alertBool = true;

    if (stringIn == "") {
        condAlert("Please fill out " + msg, alertBool);
        return true;
    } 
    else return false;
}

// String is between minimum and maximum length
// Returns true if the string passed in is between the minimum and maximum length, and alerts error message with msg if the string's length is out of bounds and msg is passed in
function validStringLength(stringIn, minLen, maxLen, msg)
{
    maxLen = Number(maxLen);
    minLen = Number(minLen);

    var alertBool = false;

    if(arguments.length > 3)
       alertBool = true;

    if (stringIn.length > maxLen) {
       condAlert("You have exceeded the limit of "+maxLen+" characters for "+msg, alertBool);
       return false;
    }

    if (stringIn.length < minLen) {
       condAlert(msg + " must be at least "+minLen+" characters long", alertBool);
       return false;
    }

    return true;
}

// RPad
// Pad the right side of the string with the provided character until it is the required length
// *U*
function rpad(stringIn, lengthIn, padChar)
{
    stringIn = String(stringIn);
    var retString = stringIn;

    for(var i = stringIn.length; i < lengthIn; i++)
    {
        retString += padChar;
    }

    return retString;
}

// LPad
// Pad the left side of the string with the provided character until it is the required length
// *U*
function lpad(stringIn, lengthIn, padChar)
{
    stringIn = String(stringIn);
    var retString = stringIn;

    for(var i = stringIn.length; i < lengthIn; i++)
    {
        retString = padChar + retString;
    }

    return retString;
}

// RTrim
// Trim the given character (or string) off of the right side of the string as many times as it recurs
// *U*
function rtrim(stringIn, trimString)
{
    stringIn = String(stringIn);

    for(var i = stringIn.length - trimString.length; i >= 0; i -= trimString.length)
    {
        if(stringIn.substr(i, trimString.length) != trimString)
        {
            return stringIn.substr(0, i + trimString.length);
        } else if(i < trimString.length)
            return stringIn.substr(0, i);
    }

    return stringIn;
}

// LTrim
// Trim the given character (or string) off of the left side of the string as many times as it recurs
// *U*
function ltrim(stringIn, trimString)
{
    stringIn = String(stringIn);

    for(var i = 0; i < stringIn.length; i += trimString.length)
    {
        if(stringIn.substr(i, min(trimString.length, stringIn.length - i)) != trimString)
        {
            return stringIn.substr(i, stringIn.length - i);
        }
    }

    return stringIn;
}

// Is a letter or number
// Returns true if the character passed in is a letter or a number, false if not
function isLetterOrNumber(charIn) {
    if(isNumber(charIn))
        return true;

    if(isLetter(charIn))
        return true;
    else
        return false;
}

// Is a letter
// Returns true if the character passed in is a letter, false if not
function isLetter(charIn) {
    if(charIn.toLowerCase() >= 'a' && charIn.toLowerCase() <= 'z')
        return true;
    else
        return false;
}

// Is a number
// Returns true if the character passed in is a number, false if not
function isNumber(charIn) {
    if(charIn >= '0' && charIn <= 9)
        return true;
    else
        return false;
}

//////////////////////////////
// User Interface Functions //
//////////////////////////////

// Conditional Alert
// Alert if alertBool is true, else do nothing
function condAlert(msg, alertBool)
{
    if(alertBool)
        alert(msg);
}

///////////////////////////////
// Data Validation Functions //
///////////////////////////////

// Validate e-mail address
// Return true if the e-mail address is valid, false if it is not
function validEmail(email)
{
    var emailFilter=/^[a-zA-Z0-9_\.\-_]+@([a-zA-Z0-9\-])+(\.([a-zA-Z0-9\-])([a-zA-Z0-9\-])+)+$/;

    return emailFilter.test(email);
}

/////////////////////////////
// Miscellaneous Functions //
/////////////////////////////

// Print Doc
// Print the current document
// *U*
function printDoc()
{
    window.print();
}

// To Do: Add Date field checks (with masks, etc) for regular text date fields (basically, for a string)
