import "../App.css"
import '@mantine/core/styles.css';
import '@mantine/dates/styles.css';  
import Sharing from './Sharing'
import dayjs from "dayjs"
import customParseFormat from 'dayjs/plugin/customParseFormat'
import { DateTimePicker } from "@mantine/dates";
import { Modal , ScrollArea, Table, Text, MantineProvider, Box, TextInput, Button, Textarea, Group, Stack} from "@mantine/core"
import { useDidUpdate, useDisclosure, useForceUpdate } from '@mantine/hooks';
import { useState } from 'react';

const List = ({value, jobs, setJobs, docname}) => {
    const days = ["THỨ HAI", "THỨ BA", "THỨ TƯ", "THỨ NĂM", "THỨ SÁU", "THỨ BẢY", "CHỦ NHẬT"]
    let maxx = Math.max(13, 1+Math.max(...[...jobs.values()].map(arr => arr.length)));
    const [time, setTime] = useState();
    const [data, setData] = useState();
    const [name, setName] = useState();
    const [content, setContent] = useState();
    const [opened, { open, close }] = useDisclosure(false);
    const [currentdate, setCurrentdate] = useState();
    const [index, setIndex] = useState();
    const forceUpdate = useForceUpdate();
    dayjs.extend(customParseFormat)
    let copy = structuredClone(jobs);
    function sort(map){
        const sortedMap = new Map([...map.entries()].sort((a, b) => {
          return dayjs(a[0], "DD-MM-YYYY").diff(dayjs(b[0], "DD-MM-YYYY"))
        }));
            [...sortedMap.entries()].map(([_, value]) => {
              value.sort((a, b) => a.time.localeCompare(b.time))
            })
        setJobs(sortedMap)
    }
    const numberofday = {
        0: 31,
        1: 28,
        2: 31,
        3: 30,
        4: 31,
        5: 30,
        6: 31,
        7: 31,
        8: 30,
        9: 31,
        10: 30,
        11: 31
    }
    const ths = (
        <Table.Tr>
            {days.map((_, i) => (
                <Table.Th p={"0 25 10 10"}>
                    <Box w = {95} h={50}>
                        <Text fz={1} c = "gray">
                            {days[i] + "\n"}
                        </Text>
                        <Text fz={25} p={0}>
                            {(() => {
                                let year = value.year()
                                if((year % 4 === 0 && year % 100 !== 0) || (year % 400 === 0))
                                    numberofday[1] = 29
                                let date = value.date()+i-(6+value.day())%7
                                if(date <= 0)
                                    date = date + numberofday[(value.month()+11)%12]
                                else if(date > numberofday[value.month()]) 
                                    date = date - numberofday[value.month()]
                                return date
                            })()}
                        </Text>
                    </Box>
                </Table.Th>
            ))}
        </Table.Tr>
    );
    useDidUpdate(() => {
        // console.log(jobs)
    }, [jobs]);
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

    function updateEvent(id){
        const APIurl = `http://localhost:8080/api/v1/event/${id}`
        let jobinfo = {
            id: null,
            name: name,
            date: dayjs(time).format("DD/MM/YYYY"),
            time: dayjs(time).format("HH:mm"),
            description: data,
            shared: false
        }
        fetch(APIurl,{
          method: 'PUT', 
          body: JSON.stringify(jobinfo),
          headers: {
            "Content-Type": 'application/json',
            "ngrok-skip-browser-warning": 1
          }
        })
    
    }
    
    function deleteItem(key, i) {
        copy = structuredClone(jobs)
        copy.get(key).splice(i, 1);
        if(copy.get(key).length === 0)
          copy.delete(key)
        jobs = copy
        sort(jobs)
    }
    
    function addJob(){
        const dateTime = dayjs(time).format("DD-MM-YYYY")
        const jobTime = dayjs(time).format("HH:mm")
        if(copy.has(dateTime)){
          copy.get(dateTime).push({
            name: name,
            time: jobTime,
            description: data
          })
        }
        else{
          copy.set(dateTime, [])
          copy.get(dateTime).push({
            name: name,
            time: jobTime,
            description: data
          })
        }
        setData()
        setName()
        setTime()
        sort(copy)
        jobs = copy
        // console.log(jobs)
    }
    const rows = [...Array(maxx)].map((_, i) => (
        <Table.Tr>
            {[...Array(7)].map((_, j) => (
                <Table.Td p={"0 0 0 10"}>
                    <Text>
                        {(() => {
                        let year = value.year()
                        let month = value.month()
                        if((year % 4 === 0 && year % 100 !== 0) || (year % 400 === 0))
                            numberofday[1] = 29
                        let date = value.date()+j-(6+value.day())%7
                        if(date <= 0){
                            date = date + numberofday[(month+11)%12]
                            month--
                        }
                        else if(date > numberofday[month]){
                            date = date - numberofday[month]
                            month++
                        }
                        if(date < 10)
                            date = '0'+date
                        if(month+1 < 10)
                            date = date+'-'+'0'+(month+1)+'-'+value.year()
                        else date =  date+'-'+(month+1)+'-'+value.year()
                        if(jobs.has(date) && jobs.get(date).length > i){    
                                                   
                            return  <Box
                                        w={100}  
                                        onClick = {() => {
                                            let currentJob = jobs.get(date)[i]
                                            let jsDate = date.split("-").reverse().join("-")
                                            setCurrentdate(date)
                                            setIndex(i)
                                            setName(currentJob.name)
                                            setData(currentJob.description)
                                            setTime(new Date(jsDate+" "+currentJob.time))
                                            open()
                                        }}
                                    >
                                        <Text>
                                            {jobs.get(date)[i].name}
                                        </Text>    
                                    </Box>
                                
                        }
                        else return <Box
                                        w={50}  
                                        h={43}  
                                    >
                                        
                                    </Box>
                        })()}
                    </Text>
                </Table.Td>
            ))}
        </Table.Tr>
    ))
    return (
        <MantineProvider>
            <ScrollArea h={650} type="never">
            <Table.ScrollContainer minWidth={100}>
                <Table 
                    horizontalSpacing={35}
                    withRowBorders={false}
                    withColumnBorders
                    highlightOnHover
                    ff = "Montserrat"
                >
                    <Table.Thead 
                        style = {{
                            borderBottom: "1px solid #dee2e6"
                        }}
                    >
                        {ths}
                    </Table.Thead>
                    <Table.Tbody>{rows}</Table.Tbody>
                </Table>
            </Table.ScrollContainer>
            </ScrollArea>
            <Modal
                title = "Chi tiết công việc"
                opened = {opened}
                onClose = {close}
            >
                <TextInput
                    // disabled
                    label = "Tên công việc"
                    value = {name} 
                    onChange = {e => {
                        setName(e.target.value)
                    }}
                    size = "xs"
                />
                <DateTimePicker
                    // disabled
                    label = "Thời gian"
                    value = {time}
                    onChange={setTime}
                    minDate={new Date()}
                    styles={{
                        day:{
                            color:"black"
                        }
                    }}
                />
                <Textarea
                    // disabled
                    label = "Mô tả"
                    value = {data}
                    onChange={e => {
                        setData(e.target.value)
                    }}
                />
                <Group 
                    justify="space-between" 
                    w={"100%"}
                    mt = {"md"}
                >
                    <Sharing docname={docname} jobs = {jobs} date = {currentdate} index = {index}/>
                    <Button.Group 
                    >
                        <Button 
                            size = {"xs"} 
                            ff = "Roboto"
                            onClick = {() => {
                                console.log("Delete", currentdate, index)
                                deleteItem(currentdate, index)
                                let id = jobs.get(currentdate)[index].id
                                deleteEvent(id)
                                close()
                            }}
                            color = "#bddcff"
                            radius={100}
                        >
                            <Text c = "black" ff="Montserrat" mt={2} fz="xs">
                                Xóa 
                            </Text>
                        </Button>
                        <Button 
                            size = {"xs"}   
                            ff = "Roboto"
                            onClick={() => {
                                const id = jobs.get(currentdate)[index].id
                                updateEvent(id)
                                deleteItem(currentdate, index)
                                addJob(time)
                                close()
                            }}
                            color = "#bddcff"
                            radius={100}
                        >
                            <Text c = "black" ff="Montserrat" mt={2} fz="xs">
                                Lưu
                            </Text>
                            </Button>
                    </Button.Group>
                </Group>
            </Modal>
        </MantineProvider>
    )
} 

export default List