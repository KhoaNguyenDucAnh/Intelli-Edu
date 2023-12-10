import React from 'react'
import dayjs from 'dayjs';
import {useState} from 'react'
import customParseFormat from 'dayjs/plugin/customParseFormat'
import { ScrollArea, List, Group, Text, ActionIcon, MantineProvider, CloseButton, Button, ThemeIcon} from '@mantine/core'
import { IconPointFilled  } from '@tabler/icons-react'
import '@mantine/core/styles.css';

const Todaylist = ({date, jobs, setJobs}) => {
  const days = ["CHỦ NHẬT", "THỨ HAI", "THỨ BA", "THỨ TƯ", "THỨ NĂM", "THỨ SÁU", "THỨ BẢY"]
  const Bulletcolor = ["#3b82f6", "#ec4899", "#fbbf24", "#9ADE7B"]
  const [color, setColor] = useState("#b8b9bc")
  dayjs.extend(customParseFormat)
  function deleteEvent(id){
    const APIurl = `http://localhost:8080/api/v1/event/${id}`
    fetch(APIurl, {
      method: 'DELETE', 
      headers: {
        "Content-Type": 'application/json',
        "ngrok-skip-browser-warning": 1
      }
    })
  }

  function sort(map){
    const sortedMap = new Map([...map.entries()].sort((a, b) => {
      return dayjs(a[0], "DD-MM-YYYY").diff(dayjs(b[0], "DD-MM-YYYY"))
    }));
        [...sortedMap.entries()].map(([_, value]) => {
          value.sort((a, b) => a.time.localeCompare(b.time))
        })
    setJobs(sortedMap)
  }
  function deleteItem(key, i) {
    let copy = structuredClone(jobs)
    const id = copy.get(key)[i].id
    deleteEvent(id)
    console.log("id", id)
    copy.get(key).splice(i, 1);
    if(copy.get(key).length === 0)
      copy.delete(key)
    jobs = copy
    sort(jobs)
  }
  
  return (
    <MantineProvider>
      <ScrollArea 
        h = {351}
        w = "100%"
        type="never"
        ff = "Montserrat"
        c = "#b8b9bc"
      >          
        <List 
          spacing={0} 
          styles={{ 
            itemWrapper: { width: '100%' }, 
            itemLabel: { width: '100%' } 
          }}
        >
          {[...jobs.entries()].map(([key, value]) => (
            <List.Item mb={10} icon = {<></>}>
              <Group>
                <Text id = "job"fw="bolder">
                  {(() => {
                    let prefix = "" 
                    if(key === dayjs(date).format('DD-MM-YYYY')){
                      prefix = "HÔM NAY"
                    }
                    else if(key === dayjs(date).add(1, 'day').format('DD-MM-YYYY')){
                      prefix = "NGÀY MAI"
                    }
                    else {
                      prefix =  days[dayjs(key.split("-").reverse().join("-")).day()]
                    }
                    return prefix
                  })()}
                </Text>
                <Text>{key.replace(/-/g, '/')}</Text>
              </Group>
              {value.map((job, i) => (
                <>
                  <List.Item 
                    icon = {
                      <ThemeIcon variant="transparent" c={Bulletcolor[Math.floor(Math.random() * Bulletcolor.length)]} >
                        <IconPointFilled/> 
                      </ThemeIcon>
                    }
                  >
                    <Text mb={5}>{job.time}</Text>
                  </List.Item>
                  <List.Item >
                    <Group ml = {50} mb = {10} justify="space-between">
                      <Text c = "white" fw={"bold"}> {job.name}</Text>
                      <CloseButton 
                        mr = {15}
                        onClick={() => deleteItem(key, i)} 
                      />
                    </Group>
                  </List.Item>
                </>
              ))}
            </List.Item>
          ))
          }
        </List>
      </ScrollArea>
    </MantineProvider>
  )
}

export default Todaylist