import React from 'react'
import dayjs from 'dayjs';
import { ScrollArea, List, Group, Text, ActionIcon, MantineProvider, CloseButton, Button} from '@mantine/core'
import { IconPointFilled  } from '@tabler/icons-react'
import '@mantine/core/styles.css';

const Todaylist = ({date, jobs, setJobs}) => {
  const days = ["CHỦ NHẬT", "THỨ HAI", "THỨ BA", "THỨ TƯ", "THỨ NĂM", "THỨ SÁU", "THỨ BẢY"]
  const Bulletcolor = ["#3b82f6", "#ec4899", "#fbbf24", "#9ADE7B"]

  function deleteItem(key, i) {
    const copy = structuredClone(jobs)
    copy.get(key).splice(i, 1);
    if(copy.get(key).length === 0)
      copy.delete(key)
    setJobs(copy);
  }
  return (
    <MantineProvider>
      <ScrollArea 
        h = {300}
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
                      <ActionIcon variant="transparent" c={Bulletcolor[Math.floor(Math.random() * Bulletcolor.length)]} >
                        <IconPointFilled/> 
                      </ActionIcon>
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