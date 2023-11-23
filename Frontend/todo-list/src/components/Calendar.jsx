import React from 'react'
import { DatePicker  } from '@mantine/dates';
import '../App.css'
import { MantineProvider } from '@mantine/core';
import dayjs from 'dayjs';
import { DemoContainer, DemoItem } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DateCalendar } from '@mui/x-date-pickers/DateCalendar';

const MyCalendar = ({value, setValue, date, setDate}) => {
  return (
      <DatePicker  
        value = {value} 
        onChange={setValue}
        size = {'md'}
        date={date}
        onDateChange={setDate}
        minDate={new Date()}
        c = "white"
      />
  )
}

export default MyCalendar