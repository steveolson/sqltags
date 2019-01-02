// FILENAME:     utilDateDropdown.js

// DESCRIPTION:  Drop-down date utility functions

// CONTENTS:     This file contains JavaScript utility functions useful for dropdown-based (or select-one-based) date field checking and maipulation

// AUTHORS:      Benjamin Lindahl (Ben@AIT-INC.com)
//               Gurney Thompson (Gurney@AIT-INC.com)

// COMPANY:      Applied Information Technologies, Inc. (http://www.ait-inc.com/)

// PROJECT:      This file is part of the JS-Lib (JavaScript Library) portion of the AITWorks project

// VERSION:      JS-Lib 0.0.1

// CO-REQUISITE INCLUDES/FILES: utilForm.js, utilDate.js, util.js

// SPECIAL USAGE INSTRUCTIONS:
//   All dates must have the following fields defined:
//   - Year selectlist with four-digit year values, with or without a blank marked as "Year" (or as set below), and suffix "_yyyy" (or as set below)
//   - Month selectlist with integer month values, with or without a blank marked as "Month" (or as set below), and suffix "_mm" (or as set below)
//   - Day selectlist with or without a blank marked as "Day" (or as set below), and suffix "_dd" (or as set below)
//   - Full date text field with suffix "_date" (or as set below)
//
//   Additionally, any date with a time component must have the following fields defined:
//   - Hour text field with suffix "_hh" (or as set below)
//   - Minutes text field with suffix "_mi" (or as set below)
//   - AM/PM selectlist with values AM, PM, Noon, and Midnight and suffix "_am" (or as set below)
//   - Time inclusion indicator text field with suffix "_time_ind" (or as set below)

// EVENT INSTRUCTIONS:
//  For each date, include the following in onLoad:
//   - addBlanks (if you want to have blanks)
//   - resizeDayList
//  For each date, include the following in onChange for the month and year:
//   - resizeDayList
//  For each date that includes a time, include the following in onChange for the AM/PM selectlist:
//   - onChangeAM
//  For each date, include the following upon any submission (whether through a button's onClick, a form's onSubmit, etc):
//   - populateDate

// FUNCTION CATEGORIES:
//   -- Date Dropdown Utility Functions:        Get general information about a particular date dropdown
//   -- Date Dropdown Check Functions:          Check date dropdown values for certain features
//   -- Date Dropdown Manipulation Functions:   Manipulate the values of a date dropdown

// PARTICULARLY USEFUL FUNCTIONS:
//   Date Dropdown Utility Functions:
//   -- xxxxValue:                              Get the value of the xxxx portion of the date, where xxxx is year, month, day, etc
//   -- setXxxxValue:                           Set the value of the Xxxx portion of the date, where Xxxx is Year, Month, Day, etc
//   -- resetXxxxValue:                         Reset the value of the Xxxx portion of the date to its default value, where Xxxx is Year, Month, Day, etc
//   -- getDate:                                Get a JavaScript Date type equivalent of the date dropdown
//
//   Date Dropdown Check Functions:
//   -- dateTimeBlank:                          Check whether a particular date dropdown is blank or not (in its entirety)
//   -- validDate:                              Check whether a particular date dropdown date portion is valid or not
//   -- validTime:                              Check whether a particular date dropdown time portion is valid or not
//   -- validDateTime:                          Check whether a particular date dropdown is valid or not (in its entirety)
//   -- datesInOrder:                           Check whether two date dropdowns are in order (the first parameter date dropdown is before the second parameter date dropdown)
//
//   Date Dropdown Manipulation Functions:
//   -- addBlanks:                              Add blank options to the selectlists
//   -- setDate:                                Set the date dropdown to the values in the JavaScript Date variable passed in
//   -- resetDate:                              Reset the date dropdown to its original values
//   -- setBlank:                               Set all options to blank
//   -- populateDate:                           Populate the date text field that will ultimately be sent to the server as the date representative


//////////////////////
// Global Variables //
//////////////////////

// Blank Values
var monthBlankText  = "Month";
var monthBlankValue = "";
var dayBlankText  = "Day";
var dayBlankValue = "";
var yearBlankText  = "Year";
var yearBlankValue = "";
var dateBlankValue = "";  // The value that the Date field should use for blanks

// Abbreviations
// These are, by default, used in the suffixes below, and are also used in format masks for "populate date"
var yearAbbrev    = "yyyy";
var monthAbbrev   = "mm";
var dayAbbrev     = "dd";
var hourAbbrev    = "hh";
var hourOfDayAbbrev = "hh24";
var minuteAbbrev  = "mi";
var amAbbrev      = "am";

// Suffixes
var yearSuffix    = "_" + yearAbbrev;
var monthSuffix   = "_" + monthAbbrev;
var daySuffix     = "_" + dayAbbrev;
var hourSuffix    = "_" + hourAbbrev;
var minuteSuffix  = "_" + minuteAbbrev;
var amSuffix      = "_" + amAbbrev;
var dateSuffix    = "_date";
var timeIndSuffix = "_time_ind";

/////////////////////////////////////
// Date Dropdown Utility Functions //
/////////////////////////////////////

// Year field
// Returns the year field in thisform with specified prefix
function yearField(thisform, prefix)
{
    return thisform.elements[prefix + yearSuffix];
}

// Year value
// Returns the value of the year field in thisform with the specified prefix
function yearValue(thisform, prefix)
{
    return fieldValue(yearField(thisform, prefix));
}

// Set Year value
// Set the value of the year field in thisform with the specified prefix
function setYearValue(thisform, prefix, newVal)
{
    setFieldValue(yearField(thisform, prefix), newVal);
    resizeDayList(thisform, prefix);
}

// Reset Year value
// Set the value of the year field in thisform with the specified prefix to the default value
function resetYearValue(thisform, prefix)
{
    setYearValue(thisform, prefix, fieldDefaultValue(yearField(thisform, prefix)));
}

// Month field
// Returns the month field in thisform with specified prefix
function monthField(thisform, prefix)
{
    return thisform.elements[prefix + monthSuffix];
}

// Month value
// Returns the value of the month field in thisform with the specified prefix
function monthValue(thisform, prefix)
{
    return fieldValue(monthField(thisform, prefix));
}

// Set Month value
// Set the value of the month field in thisform with the specified prefix
function setMonthValue(thisform, prefix, newVal)
{
    setFieldValue(monthField(thisform, prefix), newVal);
    resizeDayList(thisform, prefix);
}

// Reset Month value
// Set the value of the month field in thisform with the specified prefix to the default value
function resetMonthValue(thisform, prefix)
{
    setMonthValue(thisform, prefix, fieldDefaultValue(monthField(thisform, prefix)));
}

// Day field
// Returns the day field in thisform with specified prefix
function dayField(thisform, prefix)
{
    return thisform.elements[prefix + daySuffix];
}

// Day value
// Returns the value of the day field in thisform with the specified prefix
function dayValue(thisform, prefix)
{
    return fieldValue(dayField(thisform, prefix));
}

// Set Day value
// Set the value of the day field in thisform with the specified prefix
function setDayValue(thisform, prefix, newVal)
{
    setFieldValue(dayField(thisform, prefix), newVal);
}

// Reset Day value
// Set the value of the day field in thisform with the specified prefix to the default value
function resetDayValue(thisform, prefix)
{
    setDayValue(thisform, prefix, fieldDefaultValue(dayField(thisform, prefix)));
}

// Hour field
// Returns the hour field in thisform with specified prefix
function hourField(thisform, prefix)
{
    return thisform.elements[prefix + hourSuffix];
}

// Hour value
// Returns the value of the hour field in thisform the the specified prefix
function hourValue(thisform, prefix)
{
    return fieldValue(hourField(thisform, prefix));
}

// Set Hour value
// Set the value of the hour field in thisform with the specified prefix
function setHourValue(thisform, prefix, newVal)
{
    setFieldValue(hourField(thisform, prefix), newVal);
}

// Reet Hour value
// Set the value of the hour field in thisform with the specified prefix to the default value
function resetHourValue(thisform, prefix)
{
    setHourValue(thisform, prefix, fieldDefaultValue(hourField(thisform, prefix)));
}

// Hour of Day value
// Returns the value of the hour field in thisform the the specified prefix
function hourOfDayValue(thisform, prefix)
{
    return hourOfDay(hourValue(thisform, prefix),amValue(thisform, prefix));
}

// Set Hour of Day value
// Set the value of the hour field in thisform with the specified prefix, and the corresponding value in the AM field
function setHourOfDayValue(thisform, prefix, newVal)
{
    setHourValue(thisform, prefix, (newVal + 1) % 12 - 1);
    setAMValue(thisform, prefix, newVal < 12 ? "AM" : "PM");
}

// Reset Hour of Day value
// Reset the value of the hour field in thisform with the specified prefix, and the corresponding value in the AM field
function resetHourOfDayValue(thisform, prefix)
{
    resetHourValue(thisform, prefix);
    resetAMValue(thisform, prefix);
}

// Minute field
// Returns the minute field in thisform with specified prefix
function minuteField(thisform, prefix)
{
    return thisform.elements[prefix + minuteSuffix];
}

// Minute value
// Returns the value of the minute field in thisform with the specified prefix
function minuteValue(thisform, prefix)
{
    return fieldValue(minuteField(thisform, prefix));
}

// Set Minute value
// Set the value of the minute field in thisform with the specified prefix
function setMinuteValue(thisform, prefix, newVal)
{
    setFieldValue(minuteField(thisform, prefix), newVal);
}

// Reset Minute value
// Set the value of the minute field in thisform with the specified prefix to the default value
function resetMinuteValue(thisform, prefix)
{
    setMinuteValue(thisform, prefix, fieldDefaultValue(minuteField(thisform, prefix)));
}

// AM field
// Returns the AM field in thisform with specified prefix
function amField(thisform, prefix)
{
    return thisform.elements[prefix + amSuffix];
}

// AM value
// Returns the value of the am field in thisform with the specified prefix
function amValue(thisform, prefix)
{
    return fieldValue(amField(thisform, prefix));
}

// Set AM value
// Set the value of the am field in thisform with the specified prefix
function setAMValue(thisform, prefix, newVal)
{
    setFieldValue(amField(thisform, prefix), newVal);
}

// Reset AM value
// Set the value of the am field in thisform with the specified prefix to the default value
function resetAMValue(thisform, prefix, newVal)
{
    setAMValue(thisform, prefix, fieldDefaultValue(amField(thisform, prefix)));
}

// Date field
// Returns the date field in thisform with specified prefix
function dateField(thisform, prefix)
{
    return thisform.elements[prefix + dateSuffix];
}

// Time indicator field
// Returns the time indicator field in thisform with specified prefix
function timeIndField(thisform, prefix)
{
    return thisform.elements[prefix + timeIndSuffix];
}

// Has blanks
// Returns whether the date dropdowns have blanks, based on the months selectlist
function hasBlanks(thisform, prefix)
{
    return monthField(thisform, prefix).options[0].value == monthBlankValue;
}

// Time Exists
// Returns whether the time fields exist, based on the hours field
function timeExists(thisform, prefix)
{
    return fieldExists(thisform, prefix + hourSuffix);
}

// Get Default Date
// Returns the JavaScript Date equivalent of the defaults
// *U* - Check what happens after the length has been reset (see if the defaults remain), and otherwise work around
function getDefaultDate(thisform, prefix)
{
    var retDate = new Date(fieldDefaultValue(yearField(thisform, prefix)), fieldDefaultValue(monthField(thisform, prefix)) - 1, fieldDefaultValue(dayField(thisform, prefix)));

    if(timeExists(thisform, prefix))
    {
        retDate.setHours(hourOfDay(fieldDefaultValue(hourField(thisform, prefix)),fieldDefaultValue(amField(thisform, prefix))));
        retDate.setMinutes(fieldDefaultValue(minuteField(thisform, prefix)));
    }

    return retDate;
}

// Get Date
// Returns the JavaScript Date equivalent of the date dropdown
function getDate(thisform, prefix)
{
    var retDate = new Date(yearValue(thisform, prefix), monthValue(thisform, prefix) - 1, dayValue(thisform, prefix));

    if(timeExists(thisform, prefix))
    {
        retDate.setHours(hourOfDayValue(thisform, prefix));
        retDate.setMinutes(minuteValue(thisform, prefix));
    }

    return retDate;
}

///////////////////////////////////
// Date Dropdown Check Functions //
///////////////////////////////////

// Month Blank
// Returns true if the month is blank, false if it is not
function monthBlank(thisform, prefix)
{
    return monthValue(thisform, prefix) == monthBlankValue;
}

// Day Blank
// Returns true if the year is blank, false if it is not
function dayBlank(thisform, prefix)
{
    return dayValue(thisform, prefix) == dayBlankValue;
}

// Year Blank
// Returns true if the year is blank, false if it is not
function yearBlank(thisform, prefix)
{
    return yearValue(thisform, prefix) == yearBlankValue;
}

// Date Blank
// Returns true if the date fields are all not blank, false if any of them are blank
function dateBlank(thisform, prefix)
{
    if(monthBlank(thisform, prefix) && dayBlank(thisform, prefix) && yearBlank(thisform, prefix))
        return true;
    else
        return false;
}

// Time Blank
// Returns whether the hour and minute time fields are blank
function timeBlank(thisform, prefix)
{
    if(!timeExists(thisform, prefix))
        return true;

    if(fieldEmpty(hourField(thisform, prefix)) && fieldEmpty(minuteField(thisform, prefix)))
        return true;
    else
        return false;
}

// Date-time Blank
// Returns whether the entire date is blank
function dateTimeBlank(thisform, prefix)
{
    return (dateBlank(thisform, prefix) && timeBlank(thisform, prefix));
}

// Valid date
// Returns whether the date fields are valid
function validDate(thisform,prefix,msg)
{
    if(!hasBlanks(thisform, prefix))
    {
        return true;
    }

    var alertBool = false;
    if(arguments.length >= 3)
    {
        alertBool = true;
    }

    var numBlank = numTrue(monthBlank(thisform, prefix),dayBlank(thisform, prefix),yearBlank(thisform, prefix));
    if(numBlank != 0 && numBlank != 3)
    {
        condAlert("Please fill out the full " + msg + " date, or set the day, month, and year as the \"Blank\" values", alertBool);
        condSelectAndFocus(monthField(thisform, prefix), alertBool);
        return false;
    }

    return true;
}

// Valid time
// Returns whether the time fields are valid
function validTime(thisform,prefix,required,msg)
{
    var appendage = "";
    var requiredBool = false;

    var alertBool = false;
    if(arguments.length >= 4)
        alertBool = true;

    if(!timeExists(thisform, prefix))
    {
        condAlert("Fields for " + msg + " do not exist", alertBool);
    }

    if(arguments.length > 2)
        if(required)
            requiredBool = true;

    var minuteEmpty = fieldEmpty(minuteField(thisform, prefix));
    var hourEmpty = fieldEmpty(hourField(thisform, prefix));
    var fillError = false;

    if(requiredBool)
    {
        fillError = minuteEmpty || hourEmpty;
    } else {
        fillError = xor(minuteEmpty, hourEmpty);
        appendage = " or leave both the hour and minute fields blank";
    }

    if(fillError)
    {
        condAlert("Please fill out the complete " + msg + " time" + appendage, alertBool);
        return false;
    }

    if(!minuteEmpty)
    {
        if(!(between(minuteValue(thisform, prefix), 0, 59)))
        {
            condAlert("Please enter a valid number for Minutes in " + msg + " time", alertBool);
            return false;
        }

        if(!(between(hourValue(thisform, prefix), 1, 12)))
        {
            condAlert("Please enter a valid number for Hours in " + msg + " time", alertBool);
            return false;
        }
    }

    return true;
}

function validDateTime(thisform, prefix, timeRequired, msg)
{
    var alertBool = false;
    if(arguments.length >= 4)
    {
        alertBool = true;
    }
    var msgString = alertBool?", msg":"";

    // Because we need these functions to be sensitive to whether msg exists or not (because arguments.length is checked in the other functions)
    // I elected to use eval to avoid duplicate code
    if(eval("!validDate(thisform, prefix" + msgString + ")"))
        return false;

    if(eval("!validTime(thisform, prefix, timeRequired" + msgString + ")"))
        return false;

    if(!timeBlank(thisform, prefix) && dateBlank(thisform, prefix))
    {
        condAlert("You may not fill out the " + msg + " time without also filling out the " + msg + " date",alertBool);
        return false;
    }

    return true;
}

// Compare Date Fields
// Run compareDate on the two fields passed in
function compareDateFields(thisform1, prefix1, thisform2, prefix2)
{
    var date1 = getDate(thisform1, prefix1);
    var date2 = getDate(thisform2, prefix2);

    return compareDates(date1, date2);
}

// Dates In Order
// Check whether dates passed in are in order (equal does not count)
function datesInOrder(thisform1, prefix1, thisform2, prefix2, strictBool, msg)
{
    if(dateBlank(thisform1, prefix1) || dateBlank(thisform2, prefix2))
        return true;

    if(arguments.length < 5)
        strictBool = false;

    var alertBool = false;

    if(arguments.length >= 6)
        alertBool = true;

    var compareValue = compareDateFields(thisform1, prefix1, thisform2, prefix2);

    if (compareValue == 1 || (compareValue == 0 && strictBool))
    {
        condAlert(msg, alertBool);
        return (false);
    }
    else 
        return true;
}

//////////////////////////////////////////
// Date Dropdown Manipulation Functions //
//////////////////////////////////////////

// Add Blanks
// Adds the blank options to the month, day, and year drop-downs
// *U* - Check what happens if it's called twice
function addBlanks(thisform, prefix)
{
    if(!hasBlanks(thisform, prefix))
    {
        insertIntoSelectlist(monthField(thisform, prefix),monthBlankValue,monthBlankText,0);
        insertIntoSelectlist(dayField(thisform, prefix),dayBlankValue,dayBlankText,0);
        insertIntoSelectlist(yearField(thisform, prefix),yearBlankValue,yearBlankText,0);
        resetDate(thisform, prefix);
    }
}

// Set Date
// Sets the date dropdown values from the Date object passed in
function setDate(thisform, prefix, dateIn)
{
    setYearValue(thisform, prefix, yearFromDate(dateIn));
    setMonthValue(thisform, prefix, monthFromDate(dateIn));
    setDayValue(thisform, prefix, dayFromDate(dateIn));

    if(timeExists(thisform, prefix))
    {
        setHourValue(thisform, prefix, hourFromDate(dateIn));
        setAMValue(thisform, prefix, amFromDate(dateIn));
        setMinuteValue(thisform, prefix, minuteFromDate(dateIn));
    }
}

// Reset Date
// Resets the date dropdown values to the defaults
function resetDate(thisform, prefix)
{
    resetYearValue(thisform, prefix);
    resetMonthValue(thisform, prefix);
    resetDayValue(thisform, prefix);

    if(timeExists(thisform, prefix))
    {
        resetHourOfDayValue(thisform, prefix);
        resetMinuteValue(thisform, prefix);
    }
}

// Set Blank
// Sets the date as blank (resets all values)
function setBlank(thisform, prefix)
{
    if(hasBlanks(thisform, prefix))
    {
        setMonthValue(thisform, prefix, monthBlankValue);
        setDayValue(thisform, prefix, dayBlankValue);
        setYearValue(thisform, prefix, yearBlankValue);

        if(timeExists(thisform, prefix))
        {
            setHourValue(thisform, prefix, "");
            setMinuteValue(thisform, prefix, "");
            setAMValue(thisform, prefix, "AM");
        }
    } else {
        return false;
    }
}

// Populate Date
// Populates the overall date text field with text from the date and time fields
function populateDate(thisform,prefix,format_mask)
{
    var thisDate = dateField(thisform, prefix);

    if(dateBlank(thisform, prefix))
    {
        thisDate.value = dateBlankValue;
        timeIndField(thisform, prefix).value = 'N';
        return;
    }

    if(arguments.length < 3)
        format_mask = monthAbbrev + "/" + dayAbbrev + "/" + yearAbbrev + " " + hourAbbrev + ":" + minuteAbbrev + " " + amAbbrev;

    var dateRep = getDate(thisform, prefix);

    // Initialize to format_mask
    thisDate.value = format_mask;

    // Add the month, day, and year
    thisDate.value = thisDate.value.replace(yearAbbrev, yearFromDate(dateRep));
    thisDate.value = thisDate.value.replace(monthAbbrev, monthFromDate(dateRep));
    thisDate.value = thisDate.value.replace(dayAbbrev, dayFromDate(dateRep));

    // For if they decide to use the 24-hour time format (must come first in case hourOfDayAbbrev contains hourAbbrev, like with hh contained in hh24)
    thisDate.value = thisDate.value.replace(hourOfDayAbbrev, hourOfDayFromDate(dateRep));

    // For if they decide to use the 12-hour time format
    thisDate.value = thisDate.value.replace(hourAbbrev, hourFromDate(dateRep));
    thisDate.value = thisDate.value.replace(amAbbrev, amFromDate(dateRep));

    // Add minutes
    thisDate.value = thisDate.value.replace(minuteAbbrev, minuteFromDate(dateRep));

    if(!timeBlank(thisform,prefix))
        timeIndField(thisform, prefix).value = 'Y';
    else
        timeIndField(thisform, prefix).value = 'N';
}

// onChangeAM
// Called when the AM selectlist is changed, onChangeAM will modify the proper fields if midnight or noon are selected
function onChangeAM(thisform, prefix)
{
    var amVal = amValue(thisform, prefix);

    switch(amVal)
    {
        case "Noon":
            setNoon(thisform, prefix);
            break;
        case "Midnight":
            setMidnight(thisform, prefix);
            break;
    }
}

// set Noon
// Sets the hh, mi, and am fields to proper values for noon
function setNoon(thisform, prefix)
{
    setAMValue(thisform, prefix, "PM");
    setHourValue(thisform, prefix, "12");
    setMinuteValue(thisform, prefix, "00");
}

// set Midnight
// Sets the hh, mi, and am fields to proper values for midnight
function setMidnight(thisform, prefix)
{
    setAMValue(thisform, prefix, "AM");
    setHourValue(thisform, prefix, "12");
    setMinuteValue(thisform, prefix, "00");
}

// Resize Day List
// Sizes the day list appropriately depending on the month and year
function resizeDayList(thisform, prefix)
{
    var blanksBool = hasBlanks(thisform, prefix);

    var day = dayField(thisform, prefix);
    var month = monthField(thisform, prefix);
    var year = yearField(thisform, prefix);

    var newSize = getDaysInMonth(selectlistValue(month), selectlistValue(year)); 
    var oldSize = day.options.length;
    var oldSelectedIndex = day.selectedIndex;
    var offset = blanksBool?1:0;

    if (newSize > 0)
    {
        day.options.length = newSize + offset;
    }
    else
    {
        day.options.length = 1;
    }

    for(var i = oldSize - 1; i < newSize; i++)
    {
        day.options[i + offset].value = i + 1;
        day.options[i + offset].text = i + 1;
    }

    if(oldSelectedIndex >= newSize)
    {
        day.selectedIndex = newSize - 1 + offset;
    }
}

