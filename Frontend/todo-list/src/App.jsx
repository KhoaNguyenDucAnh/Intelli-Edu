import './App.css'
import Todaylist from './components/Todaylist';
import MyCalendar from './components/Calendar'
import List from './components/List'
import Sharing from './components/Sharing'
import React from 'react'
import dayjs from 'dayjs';
import { IconSun, IconMoon, IconChevronLeft,  IconChevronRight, IconPlus} from '@tabler/icons-react';
import { Text, Group, Stack, Button, Modal, Textarea, MantineProvider, TextInput, useMantineColorScheme, useComputedColorScheme, ActionIcon} from '@mantine/core';
import { useState } from 'react';
import { useDisclosure } from '@mantine/hooks';
import { DateTimePicker } from '@mantine/dates';
// import cx from 'clsx';
import '@mantine/core/styles.css';
import '@mantine/dates/styles.css';

function App(){
  let createAPI = "https://intelli-edu.onrender.com/api/v1/event"
  const [value, setValue] = useState(dayjs())
  const [name, setName] = useState("");
  const [date, setDate] = useState(new Date());
  const [data, setData] = useState('');
  const [title, setTitle] = useState('');
  const [time, setTime] = useState();
  const [jobs, setJobs] = useState(new Map());
  const [opened, { open, close }] = useDisclosure(false);
  const {setColorScheme} = useMantineColorScheme();
  const computedColorScheme = useComputedColorScheme('light');
  function sort(map){
    const sortedMap = new Map([...map.entries()].sort((a, b) => a[0].localeCompare(b[0])));
    [...sortedMap.entries()].map(([_, value]) => {
      value.sort((a, b) => a.time.localeCompare(b.time))
    })
    setJobs(sortedMap)
  }
  const toggleScheme = () => {
    let todaylist = document.getElementById("todaylist")
    if(computedColorScheme === "dark"){
      setColorScheme("light")
      // todaylist.style.backgroundColor = "#18181b"
    }
    else{
      setColorScheme("dark")
      // todaylist.style.backgroundColor = "#18181b"
    }
    
    
  }
   function createEvent(data){
    fetch(url, {
      method: 'POST', 
      body: JSON.stringify(data),
    })
    .then(response => response.json())
    .then(data => console.log(data))
    .catch((error) => {
      console.error('Error:', error);
    });
   }

  function addJob(){
    const dateTime = dayjs(time).format("DD-MM-YYYY")
    const jobTime = dayjs(time).format("HH:mm")
    if(jobs.has(dateTime)){
      jobs.get(dateTime).push({
        name: title,
        time: jobTime,
        description: data,
        id: null
      })
    }
    else{
      jobs.set(dateTime, [])
      jobs.get(dateTime).push({
        name: title,
        time: jobTime,
        description: data,
        id : null
      })
    }
    let data = {
      name: title,
      deadline: dateTime + " " + jobTime,
      description: data,
      shared: false
    }
    // createEvent(data)
    setData('')
    setTitle('')
    setTime()
    sort(jobs)
    close();
  }
  return (
    <MantineProvider theme={{ colorScheme: 'dark'}}>
        <Group spacing = {0} justify="space-between">
          <Stack 
            id = "todaylist"
            align='center' 
            justify='space-between'
            h = "100vh"
            w = {330}
            bg = "#18181b"
          >
            <Group justify="flex-end" w = "100%">  
              {/* <ActionIcon 
                onClick={toggleScheme} 
                size= {40}
                mt = {10}
                ml = {10}
              >
                {computedColorScheme === "light" ? <IconMoon size={18}/> : <IconSun size={18}/>}
              </ActionIcon> */}
              <Button 
                radius={10}
                onClick = {() => {
                  setTime(new Date(value))
                  open()
                }}
                size = "xs"
                mr = {15}
                mt = {10}
                ff = "Roboto"
                color = "#74747c"
              >
                <IconPlus/> 
              </Button>
            </Group>
            <Modal 
              opened={opened} 
              onClose={close} 
              title='Add your deadline' 
              centered
              size = {500}    
            >
              <TextInput
                label = "Tên công việc"
                value = {title} 
                onChange = {(e) => setTitle(e.target.value)}
                mt = {10}
                withAsterisk
                required
                // error = "Không thể bỏ trống"
              />
              <DateTimePicker
                defaultValue={new Date(value)}
                withAsterisk
                // error = "Không thể bỏ trống"
                label="Chọn ngày và thời gian"
                placeholder="Pick date and time"
                clearable
                value={time}
                onChange={setTime}
                mt = {15}
                minDate={new Date()}
                required
              />
              <Textarea
                label = "Mô tả công việc"
                value = {data} 
                onChange = {(e) => setData(e.target.value)}
                mt = {15}
                autosize
                withAsterisk
                required
              />
              <Button
                ml = "80%"
                mt={15}
                onClick={addJob}
              >
                Submit
              </Button>
            </Modal>
            <MantineProvider theme={{ colorScheme: 'dark' }}>
              <MyCalendar value = {value} setValue = {setValue} date={date} setDate={setDate}/>
            </MantineProvider>
            <Todaylist value = {value} jobs = {jobs} setJobs={setJobs}/>
          </Stack>
          <Stack h = "100vh">
            <Group justify='space-between' mt={5}>
              <Button.Group mt = {5} >
                <Button 
                  color = "#f4f4f5"
                  mr = {1} 
                  radius = {10}
                  size = "xs"
                  onClick={() => {
                    setValue(dayjs(value).subtract(1, 'week'))
                    setDate(dayjs(value).subtract(1, 'week').toDate())
                  }}
                >
                  <IconChevronLeft color = "black"/>
                </Button>
                
                <Button 
                  color = "#f4f4f5"
                  ml = {1} 
                  radius = {10}
                  size = "xs"
                  onClick={() => {
                    setValue(dayjs(value).add(1, 'week'))
                    setDate(dayjs(value).add(1, 'week').toDate())
                  }}
                >
                  <IconChevronRight color = "black"/>
                </Button>
              </Button.Group>
              <TextInput
                size = "xs"
                variant="unstyled"
                value = {name}
                onChange = {e => {
                  setName(e.target.value)
                }}
                placeholder = "Untitled"
                style = {{
                  width: 200
                }}
              />
              <Sharing docname = {name} jobs = {jobs}/>
            </Group>
            <List value = {dayjs(value)} jobs = {jobs} setJobs = {setJobs}/>
          </Stack>
        </Group>  
     </MantineProvider>
  );
}

export default App
