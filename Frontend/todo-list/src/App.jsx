import './App.css'
import classes from './Demo.module.css';
import Todaylist from './components/Todaylist';
import MyCalendar from './components/Calendar'
import List from './components/List'
import React from 'react'
import dayjs from 'dayjs';
import customParseFormat from 'dayjs/plugin/customParseFormat'
import { IconSun, IconMoon, IconChevronLeft,  IconChevronRight, IconPlus, IconPhoto, IconListDetails, IconClockExclamation} from '@tabler/icons-react';
import { Tabs, rem, Text, Group, Stack, Button, Modal, Textarea, MantineProvider, TextInput, useMantineColorScheme, useComputedColorScheme, ActionIcon, Slider, Switch, Fieldset, NativeSelect} from '@mantine/core';
import { useState, useEffect } from 'react';
import { useDisclosure } from '@mantine/hooks';
import { DateTimePicker } from '@mantine/dates';
import '@mantine/core/styles.css';
import '@mantine/dates/styles.css';
import '@mantine/notifications/styles.css';

function App(){
  const iconStyle = { width: rem(20), height: rem(20) };
  const APIurl = "http://localhost:8080/api/v1/event"
  const [value, setValue] = useState(dayjs())
  const [name, setName] = useState("");
  const [date, setDate] = useState(new Date());
  const [data, setData] = useState('');
  const [title, setTitle] = useState('');
  const [time, setTime] = useState();
  const [jobs, setJobs] = useState(new Map());
  const [important, setImportant] = useState(false)
  const [urgent, setUrgent] = useState(false)
  const [opened, { open, close }] = useDisclosure(false);
  const {setColorScheme} = useMantineColorScheme();
  const computedColorScheme = useComputedColorScheme('light');
  const priorityTask = []
  dayjs.extend(customParseFormat)
  function convert(data){
    console.log("GET Data:", data)
    Object.entries(data).map(element => {
      let key = dayjs(element[0], "D/M/YYYY").format("DD-MM-YYYY")
      console.log(key)
      jobs.set(key, element[1])
    })
    sort(jobs)
  }
  function getData(){
    fetch(APIurl, {
      method: 'GET', 
      headers: {
        "Content-Type": 'application/json',
        "ngrok-skip-browser-warning": 1
      }
    })
    .then(response => response.json())
    .then(data => {convert(data)})
    .catch((error) => {
      console.error('Error:', error);
    });
  }
  useEffect(() => {
    getData()
  }, [])
  function sort(map){
    const sortedMap = new Map([...map.entries()].sort((a, b) => {
      return dayjs(a[0], "DD-MM-YYYY").diff(dayjs(b[0], "DD-MM-YYYY"))
    }));
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
   function createEvent(job){
    console.log(JSON.stringify(job))
    fetch(APIurl, {
      method: 'POST', 
      body: JSON.stringify(job),
      headers: {
        "Content-Type": 'application/json',
        "ngrok-skip-browser-warning": 1
      }
    })
    .then(response => response.json())
    .then(data => {
      console.log("POST data:", data)
      console.log("Jobs:", jobs)
      let dataDate = dayjs(data.date, "D/M/YYYY").format("DD-MM-YYYY")
      console.log(jobs.get(dataDate))
      let len = [...jobs.get(dataDate)].length
      jobs.get(dataDate)[len-1].id = data.id
    })
    .catch((error) => {
      console.error('Error:', error);
    });
   }

  function addJob(){
    const dateTime = dayjs(time).format("DD-MM-YYYY")
    const jobTime = dayjs(time).format("HH:mm")
    let jobstatus = {
      name: title,
      time: jobTime,
      description: data,
      id : null,
      urgent: urgent,
      important: important,
      // priority: (urgent && important)? 1 : (important)? 2 : (urgent)? 3 : 4
    }
    if(jobs.has(dateTime)){
      jobs.get(dateTime).push(jobstatus)
    }
    else{
      jobs.set(dateTime, [])
      jobs.get(dateTime).push(jobstatus)
    }
    let jobinfo = {
      id: null,
      name: title,
      date: dayjs(time).format("DD/MM/YYYY"),
      time: dayjs(time).format("HH:mm"),
      description: data,
      shared: false,
      urgent: urgent,
      important: important
    }
    console.log("Job Info:", jobinfo)
    createEvent(jobinfo)
    setData('')
    setTitle('')
    setTime()
    setImportant(false)
    setUrgent(false)
    sort(jobs)
    close();
    console.log(jobs)
  }
  return (
    <MantineProvider theme={{ colorScheme: 'dark'}}>
        <Group spacing = {0} justify="space-between">
          <Stack 
            id = "todaylist"
            align='center' 
            justify='space-between'
            h = {"100%"}
            w = {"25%"}
            bg = "#18181b"
          >
            <Stack h="100%">
              <Group justify="flex-end" w = "100%" h="50%">  
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
              <MyCalendar value = {value} setValue = {setValue} date={date} setDate={setDate}/>
            </Stack>
            <Modal 
              opened={opened} 
              onClose={close} 
              title='Thêm công việc vào lịch trình' 
              centered
              size = {500}    
            >
              <Fieldset legend="Điền công việc">
                <TextInput
                  label = "Tên công việc"
                  value = {title} 
                  onChange = {(e) => setTitle(e.target.value)}
                  mt = {10}
                  withAsterisk
                  required
                />
                <DateTimePicker
                  defaultValue={new Date(value)}
                  withAsterisk
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
                <Group mt = {15} justify='space-between'>
                  <Stack gap = {0}>
                    <Text withAsterisk>Công việc có quan trọng?</Text>
                    <Switch
                      withAsterisk
                      value = {important}
                      onChange = {(e) => setImportant(e.target.checked)}
                    />
                  </Stack>

                  <Stack gap = {0}>
                    <Text withAsterisk>Công việc có cấp bách?</Text>
                    <Switch
                      withAsterisk
                      value = {urgent}
                      onChange = {(e) => setUrgent(e.target.checked)}
                    />
                  </Stack>
                </Group>
                
                <Group 
                  mt = {15}
                  justify='flex-end'
                >
                  <Button
                    onClick={() => {
                      addJob()
                    }}
                  >
                    Submit
                  </Button>
                </Group>
              </Fieldset>
            </Modal>
            <Todaylist value = {value} jobs = {jobs} setJobs={setJobs} mode={0}/>
          </Stack>
          <Stack h = {"100%"} w = "73%">
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
                mr = {100}
              />
              <Text/>
            </Group>
            <List value = {dayjs(value)} jobs = {jobs} setJobs = {setJobs} docname = {name}/>
          </Stack>
        </Group>  
     </MantineProvider>
  );
}

export default App
