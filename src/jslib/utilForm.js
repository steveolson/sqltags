// FILENAME:     utilForm.js

// DESCRIPTION:  JavaScript form utility functions

// CONTENTS:     his file contains utility functions useful for form checking and manipulation

// AUTHORS:      Benjamin Lindahl (Ben@AIT-INC.com)
//               Gurney Thompson (Gurney@AIT-INC.com)

// COMPANY:      Applied Information Technologies, Inc. (http://www.ait-inc.com/)

// PROJECT:      This file is part of the JS-Lib (JavaScript Library) portion of the AITWorks project

// VERSION:      JS-Lib 0.0.1

// CO-REQUISITE INCLUDES/FILES: util.js

// SPECIAL USAGE INSTRUCTIONS: None

// FUNCTION CATEGORIES:
//   -- General Form Field Functions:       General utilities that allow data getting and setting from fields of any field type
//   -- Focus and Select Functions:         Placing the cursor in fields and selecting contents
//   -- Text Field Functions:               Utilities for use on text fields (text, textarea, password)
//   -- Select List Functions:              Functions for dealing with select lists
//   -- Radio Button Functions:             Functions for dealing with radio buttons
//   -- Checkbox Functions:                 Functions for dealing with checkboxes

// PARTICULARLY USEFUL FUNCTIONS:
//   General Form Field Functions:
//   -- fieldExists:                        Find out if a field exists (useful for when fields do not always appear because of server-side logic)
//   -- fieldValue:                         Get the value of the field, regardless of type
//   -- fieldDefaultValue:                  Get the default value of the field, regardless of type
//   -- setFieldValue:                      Set the value of the field, regardless of type
//   -- fieldEmpty:                         Check whether a particular field is empty, regardless of type (optionally display an error message if it is)
//
//   Focus and Select Functions:
//   -- condSelectAndFocus:                 Select and focus the cursor on a particular field
//   -- focusNthField:                      Focus the cursor on the nth field in the form (also possibly to do it only on particular types)
//   -- focusFirstEmpty:                    Focus the cursor on the first empty field in the form (also possible to do it only on particular types)
//
//   Text Field Functions:
//   -- validFieldLength:                   Check whether the field's value is between the minimum and maximum lengths
//
//   Select List Functions:
//   -- insertIntoSelectlist:               Insert a new option into a particular selectlist


//////////////////////////////////
// Global Variable Declarations //
//////////////////////////////////

// Form Field Type Class Definitions
var typeClasses = new Array();
typeClasses["GENERALTEXT"] = new Array("text","textarea","password", "fileUpload");
typeClasses["GENERALSELECT"] = new Array("select-one","select-multiple");
typeClasses["GENERALBUTTON"] = new Array("button","submit","reset");

//////////////////////////////////
// General Form Field Functions //
//////////////////////////////////

// Field Exists
// Return true if the field exists, false if it does not
function fieldExists(thisform, fieldName)
{
    for(var i = 0; i < thisform.length; i++)
    {
        if(thisform.elements[i].name == fieldName)
            return true;
    }

    return false;
}

// Field of Type
// Return true if the field is of the type passed in, fase if it is not.  This function also contains some "special-purpose" types, and users can define their own by passing an array into typeIn
function fieldOfType(thisField, typeIn, hiddenBool)
{
    if(arguments.length < 3)
        hiddenBool = true;

    if(isArray(thisField))
    {
        if(thisField.length >= 1)
            return fieldOfType(thisField[0], typeIn, hiddenBool);
        else
            return false;
    }

    var fieldType = thisField.type.toUpperCase();

    if(fieldType == "HIDDEN" && !hiddenBool)
        return false;

    if(isArray(typeIn))
    {
        return arrayContainsInsensitive(typeIn, fieldType);
    }

    typeIn = typeIn.toUpperCase();

    // Built-in case: This case is for if typeIn is a built-in field type (not one of the user-defined ones below)
    if(typeIn == fieldType)
        return true;

    // User-defined cases: 
    if(typeIn == "ALL")
        return true;

    // To Do: Make it so that this never throws errors regardless of whether typeClasses contains an index of type typeIn or not
    var typeArray = typeClasses[typeIn];

    if(isArray(typeArray))
        return fieldOfType(thisField, typeArray);

    return false;
}

// Field Value
// Return the value of the field, regardless of type
function fieldValue(thisField)
{
    if(fieldOfType(thisField, "generalSelect"))
        return selectlistValue(thisField);

    if(fieldOfType(thisField, "radio"))
        return radioValue(thisField);

    if(fieldOfType(thisField, "checkbox"))
        return checkboxValue(thisField);

    return textValue(thisField);
}

// Field Default Value
// Return the default value of the field, regardless of type
function fieldDefaultValue(thisField)
{
    if(fieldOfType(thisField, "generalSelect"))
        return selectlistDefaultValue(thisField);

    if(fieldOfType(thisField, "radio"))
        return radioDefaultValue(thisField);

    if(fieldOfType(thisField, "checkbox"))
        return checkboxDefaultValue(thisField);

    if(fieldOfType(thisField, "hidden"))
        return thisfield.value;

    return textDefaultValue(thisField);
}

// Set Field Value
// Set the value of the field, regardless of type
function setFieldValue(thisField, valueIn)
{
    if(fieldOfType(thisField, "generalSelect"))
        return setSelectlistValue(thisField, valueIn);

    if(fieldOfType(thisField, "radio"))
        return setRadioValue(thisField, valueIn);

    if(fieldOfType(thisField, "checkbox"))
        return setCheckboxValue(thisField, valueIn);

    thisField.value = valueIn;
    return true;
}

// Field is empty
// Returns true if the field is empty, and displays error message with msg if msg is provided
function fieldEmpty(thisField, msg)
{
    var alertBool = false;
    if(arguments.length >= 2)
        alertBool = true;
    var msgString = alertBool?", msg":"";

    if(eval("stringEmpty(fieldValue(thisField)" + msgString + ")"))
    {
        condSelectAndFocus(thisField, alertBool);
        return true;
    } else return false;
}

////////////////////////////////
// Focus and Select Functions //
////////////////////////////////

// Select field
// Select all the data in a field if it is of the generaltext type
function selectField(field)
{
    if(fieldOfType(field,"generalText"))
        field.select();
}

// Conditional focus
// Focus if focusBool is true
function condFocus(field, focusBool)
{
    if(focusBool)
    {
        field.focus();
    }
}

// Conditional select
// Focus if selectBool is true
function condSelect(field, selectBool)
{
    if(selectBool)
    {
        selectField(field);
    }
}

function condSelectAndFocus(field, selectBool, focusBool)
{
    if(arguments.length < 2)
        selectBool = true;

    if(arguments.length < 3)
        focusBool = true;

    condSelect(field, selectBool);
    condFocus(field, focusBool);
}

// Focus Nth Field
// Set the focus of the form to the nth field of type typeIn in the page's forms
// *U* - Default situations
function focusNthField(n, typeIn, selectBool)
{
    if(arguments.length < 1)
        n = 1;

    if(arguments.length < 2)
        typeIn = "all";

    if(arguments.length < 3)
        selectBool = true;

    var currNum = 0;

    for(var i = 0; i < document.forms.length; i++)
    {
        var currForm = document.forms[i];
        for(var j = 0; j < currForm.elements.length; j++)
        {
            var currElement = currForm.elements[j];
            if(fieldOfType(currElement, typeIn.toUpperCase(), false))
            {
                currNum++;
                if(currNum == n)
                {
                    condSelect(currElement, selectBool);
                    currElement.focus();
                    return currElement;
                }
            }
        }
    }

    if(n > 1)
        return focusNthField(1, typeIn, selectBool);
    else if(typeIn.toUpperCase() != "ALL")
        return focusNthField(1, "all", selectBool);
    else
        return false;
}

// Focus First Empty
// Set the focus of the form to the first empty field of type typeIn in the page's forms
// *U* - Default situations
function focusFirstEmpty(typeIn, selectBool)
{
    if(arguments.length < 1)
        typeIn = "all";

    if(arguments.length < 2)
        selectBool = true;

    typeIn = typeIn.toUpperCase();
    var oldTypeIn = "";
    var breakOut = false;

    while(!breakOut)
    {
        for(var i = 0; i < document.forms.length; i++)
        {
            var currForm = document.forms[i];
            for(var j = 0; j < currForm.elements.length; j++)
            {
                var currElement = currForm.elements[j];

                if(fieldOfType(currElement, typeIn, false))
                {
                    if(fieldValue(currElement) == "")
                    {
                        condSelect(currElement, selectBool);
                        currElement.focus();
                        return currElement;
                    }
                }
            }
        }

        if(typeIn == "ALL")
            breakOut = true;
        else
        {
            oldTypeIn = typeIn;
            typeIn = "ALL";
        }
    }

    return focusNthField(1, oldTypeIn, selectBool);
}

//////////////////////////
// Text Field Functions //
//////////////////////////

// Text Value
// Returns the value of the general text field passed in
function textValue(thisField)
{
    if(isArray(thisField))
    {
        debugAlert("More than one general text field with the same name.\ntextValue aborting.");
        return null;
    }

    return thisField.value;
}

// Text Default Value
// Returns the default value of the general text field passed in
function textDefaultValue(thisField)
{
    if(isArray(thisField))
    {
        debugAlert("More than one general text field with the same name.\ntextDefaultValue aborting.");
        return null;
    }

    return thisField.defaultValue;
}

// Set Text Value
// Returns the default value of the general text field passed in
// *U*
function setTextValue(thisField)
{
    if(isArray(thisField))
    {
        debugAlert("More than one general text field with the same name.\nsetTextValue aborting.");
        return null;
    }

    return thisField.defaultValue;
}

// Validate field length
// Returns true if the field length is between minLen and maxLen, or return false if it is outside of the range, and focuses if msg is provided
function validFieldLength(thisField, minLen, maxLen, msg)
{
    // To Do: Check if this may be construed to have any meaning for other field types
    var alertBool = false;

    if(arguments.length >= 4)
        alertBool = true;
    var msgString = alertBool?", msg":"";

    if(eval("!validStringLength(thisField.value, minLen, maxLen" + msgString + ")"))
    {
        condSelectAndFocus(thisField, alertBool);
        return false;
    }

    return true;
}

///////////////////////////
// Select List Functions //
///////////////////////////

// Get selectlist value
// Returns the value of the selectlist field passed in
function selectlistValue(thisField)
{
    if(isArray(thisField))
    {
        debugAlert("More than one select list with the same name.\nselectlistValue aborting.");
        return null;
    }

    if(fieldOfType(thisField, "select-one"))
        return thisField.options[thisField.selectedIndex].value;

    var retArr = new Array();

    for(var i = 0; i < thisField.options.length; i++)
    {
        if(thisField.options[i].selected)
            insertIntoArray(retArr, thisField.options[i].value);
    }

    if(retArr.length == 1)
        return retArr[0];
    else
        return retArr;
}

// Get selectlist default value
// Returns the default value of the selectlist field passed in
function selectlistDefaultValue(thisField)
{
    // To Do: Check what happens if an option goes away and comes back

    if(isArray(thisField))
    {
        debugAlert("More than one select list with the same name.\nselectlistDefaultValue aborting.");
        return null;
    }

    var retArr = new Array();

    for(var i = 0; i < thisField.options.length; i++)
    {
        if(thisField.options[i].defaultSelected)
            insertIntoArray(retArr, thisField.options[i].value, retArr.length);
    }

    if(retArr.length == 0)
    {
        if(fieldOfType(thisField, "select-multiple"))
            return retArr;

        if(thisField.options.length >= 1)
            return thisField.options[0].value;
        else
            return "";
    }
    else if(retArr.length == 1)
        return retArr[0];
    else
        return retArr;
}

// Set selectlist value
// Sets the value of the selectlist to valueIn if valueIn is in the selectlist
function setSelectlistValue(thisField, valueIn)
{
    // To Do: Make this work so that you can pass in multiple values in an array
    for(var i = 0; i < thisField.options.length; i++)
    {
        if(thisField.options[i].value == valueIn)
        {
            thisField.selectedIndex = i;
            return true;
        }
    }

    return false;
}

// Insert Into Selectlist
// Inserts the given text and value pair into the selectlist at position indicated by pos
function insertIntoSelectlist(thisField, valueIn, textIn, pos)
{
    if(isArray(thisField))
    {
        debugAlert("More than one select list with the same name.\ninsertIntoSelectlist aborting.");
        return null;
    }

    var oldSelectedIndex = thisField.selectedIndex;

    var thisOptions = thisField.options;
    thisOptions.length++;

    if(pos >= thisOptions.length)
        pos = thisOptions.length - 1;

    for(var i = thisOptions.length - 1; i > pos; i--)
    {
        thisOptions[i].text = thisOptions[i - 1].text;
        thisOptions[i].value = thisOptions[i - 1].value;
        thisOptions[i].defaultSelected = thisOptions[i - 1].defaultSelected;
        thisOptions[i].selected = thisOptions[i - 1].selected;
    }

    thisOptions[pos].value = valueIn;
    thisOptions[pos].text = textIn;
    thisOptions[pos].defaultSelected = false;
    thisOptions[pos].selected = false;

    if(oldSelectedIndex >= pos)
        thisField.selectedIndex = oldSelectedIndex + 1;

    // To Do: Look further into treating built-in arrays as generic arrays (especially options)
    // You can't treat it as a regular array, apparently, because of reference issues and conflicts, and a "Failure" exception is thrown when in Netscape
    // insertIntoArray(thisField.options, new Option(textIn, valueIn), pos);
}

////////////////////////////
// Radio Button Functions //
////////////////////////////

// Radio Value
// Get the value of the radio button
// *U*
function radioValue(thisField)
{
    for(var i = 0; i < thisField.length; i++)
    {
        if(thisField[i].checked)
            return thisField[i].value;
    }

    return null;
}

// Radio Default Value
// Get the default value of the radio button
// *U*
function radioDefaultValue(thisField)
{
    for(var i = 0; i < thisField.length; i++)
    {
        if(thisField[i].defaultChecked)
            return thisField[i].value;
    }

    return null;
}

// Set Radio Value
// Set the value of the radio button
// *U*
function setRadioValue(thisField, valueIn)
{
    for(var i = 0; i < thisField.length; i++)
    {
        if(thisField[i].value == valueIn)
        {
            for(var j = 0; j < thisField.length; j++)
            {
                thisField[j].checked = false;
            }

            thisField[i].checked = true;
            return true;
        }
    }

    return false;
}

////////////////////////
// Checkbox Functions //
////////////////////////

// Checkbox Value
// Get the value of the checkbox
// *U*
function checkboxValue(thisField)
{
    var retArr = new Array();

    for(var i = 0; i < thisField.length; i++)
    {
        if(thisField[i].checked)
            insertIntoArray(retArr, thisField[i].value);
    }

    if(retArr.length == 1)
        return retArr[0];
    else
        return retArr;
}

// Checkbox Default Value
// Get the default value of the checkbox
// *U*
function checkboxDefaultValue(thisField)
{
    var retArr = new Array();

    for(var i = 0; i < thisField.length; i++)
    {
        if(thisField[i].defaultChecked)
            insertIntoArray(retArr, thisField[i].value);
    }

    if(retArr.length == 1)
        return retArr[0];
    else
        return retArr;
}

// Set Checkbox Value
// Set the value of the checkbox (i.e., check the checkbox(es) with value(s) in valueIn, and uncheck all others)
// *U*
function setCheckboxValue(thisField, valueIn)
{
    uncheckCheckbox(thisField);
    checkCheckbox(thisField, valueIn);
}

// Get Checkbox With Value
// Get the checkbox from the array of checkboxes that has the particular value passed in
// *U*
function getCheckboxWithValue(thisField, valueIn)
{
    if(!isArray(thisField))
    {
        if(arrayContains(valueIn, thisField.value))
            return thisField;
        else
            return null;

        if(thisField.value == valueIn)
            return thisField;
        else
            return null;
    } else {
        if(isArray(valueIn))
        {
            var retArr = new Array();

            for(var i = 0; i < thisField.length; i++)
            {
                if(arrayContains(valueIn, thisField[i].value))
                    insertIntoArray(retArr, thisField[i]);
            }

            if(retArr.length == 1)
                return retArr[0];
            else
                return retArr;
        } else {
            for(var i = 0; i < thisField.length; i++)
            {
                if(thisField[i].value == valueIn)
                    return thisField[i];
            }
        }

        return null;
    }
}

// Checkbox Checked
// Get whether the checkbox is checked or not (if valueIn is passed in, it will check for that particular value
// *U*
function checkboxChecked(thisField, valueIn)
{
    var thisCheckbox;

    if(arguments.length >= 2)
    {
        // Case: They passed in a value and either a single checkbox or an array of checkboxes
        thisCheckbox = getCheckboxWithValue(thisField, valueIn);

        // Case: No checkbox in thisField exists with value == valueIn
        if(thisCheckbox == null)
            return false;
    } else {
        thisCheckbox = thisField;
    }

    // One of two things happened here:
    // 1) They provided an array of checkboxes, but no value
    // 2) They provided an array of checkboxes and an array of values, and more than one checkbox had a value in that array
    // Return true only if all checkboxes in thisCheckbox are true, false otherwise
    if(isArray(thisCheckbox))
        for(var i = 0; i < thisField.length; i++)
            if(!checkboxChecked(thisCheckbox[i]))
                return false;

    return true;

    return thisCheckbox.checked;
}

// Set Checkbox Checked
// Set the checkbox checked boolean to the boolean passed in
// *U*
function setCheckboxChecked(thisField, boolIn, valueIn)
{
    var thisCheckbox;

    if(arguments.length >= 3)
    {
        thisCheckbox = getCheckboxWithValue(thisField, valueIn);

        if(thisCheckbox == null)
            return false;
    } else {
        thisCheckbox = thisField;
    }

    // One of two things happened here:
    // 1) They provided an array of checkboxes, but no value
    // 2) They provided an array of checkboxes and an array of values, and more than one checkbox had a value in that array
    // Set all checkboxes to the boolean passed in
    if(isArray(thisCheckbox))
    {
        for(var i = 0; i < thisField.length; i++)
        {
            if(!checkboxChecked(thisCheckbox[i]))
                thisCheckbox[i].checked = boolIn;
        }
    }

    thisCheckbox.checked = boolIn;
}

// Check Checkbox
// Check the checkbox for this particular value
// *U*
function checkCheckbox(thisField, valueIn)
{
    if(arguments.length >= 2)
        return setCheckboxChecked(thisField, true, valueIn);
    else
        return setCheckboxChecked(thisField, true);
}

// Uncheck Checkbox
// Uncheck the checkbox for this particular value
// *U*
function uncheckCheckbox(thisField, valueIn)
{
    if(arguments.length >= 2)
        return setCheckboxChecked(thisField, false, valueIn);
    else
        return setCheckboxChecked(thisField, false);
}

// Toggle Checkbox
// Toggle the checked value for checkbox
// *U*
function toggleCheckbox(thisField, valueIn)
{
    var thisCheckbox;

    if(arguments.length >= 2)
    {
        thisCheckbox = getCheckboxWithValue(thisField, valueIn);

        if(thisCheckbox == null)
            return false;
    } else
        thisCheckbox = thisField;

    if(isArray(thisCheckbox))
    {
        for(var i = 0; i < thisCheckbox.length; i++)
        {
            toggleCheckbox(thisCheckbox[i]);
        }

        return true;
    }

    if(thisCheckbox.checked)
        uncheckCheckbox(thisCheckbox);
    else
        checkCheckbox(thisCheckbox);

    return true;
}

