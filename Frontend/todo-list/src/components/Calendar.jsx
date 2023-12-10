import React from 'react'
import { DatePicker  } from '@mantine/dates';
import '../App.css'
import { MantineProvider } from '@mantine/core';
import dayjs from 'dayjs';

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
        styles = {{
          day:{
            color: "#b8b9bc"
          }
          
        }}
      />
  )
}

export default MyCalendar