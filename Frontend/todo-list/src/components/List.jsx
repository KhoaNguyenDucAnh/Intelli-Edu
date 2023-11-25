import { Modal , ScrollArea, Table, Text, MantineProvider, Box, TextInput, Button, Textarea} from "@mantine/core"
import { useDidUpdate, useDisclosure, useForceUpdate } from '@mantine/hooks';
import { useState } from 'react';
import '@mantine/core/styles.css';
import '@mantine/dates/styles.css';  
import dayjs from "dayjs"
import "../App.css"
import { DateTimePicker } from "@mantine/dates";
import { modals } from '@mantine/modals';

const List = ({value, jobs, setJobs}) => {
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

    let copy = structuredClone(jobs);
    function sort(map){
        const sortedMap = new Map([...map.entries()].sort((a, b) => a[0].localeCompare(b[0])));
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
        console.log("delete:", id)
        fetch(APIurl, {
          method: 'DELETE', 
          headers: {
            "Content-Type": 'application/json',
            "ngrok-skip-browser-warning": 1
          }
        })
    }

    function createEvent(job){
        const APIurl = "http://localhost:8080/api/v1/event"
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
            let len = [...jobs.get(data.date.replace(/\//g, '-'))].length
            jobs.get(data.date.replace(/\//g, '-'))[len-1].id = data.id
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    }
    
    function deleteItem(key, i) {
        copy = structuredClone(jobs)
        const id = copy.get(key)[i].id
        console.log("objs", copy.get(key), "obj", copy.get(key)[i])
        deleteEvent(id)
        copy.get(key).splice(i, 1);
        if(copy.get(key).length === 0)
          copy.delete(key)
        setJobs(copy);
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
        let jobinfo = {
            id: null,
            name: name,
            date: dayjs(time).format("DD/MM/YYYY"),
            time: dayjs(time).format("HH:mm"),
            description: data,
            shared: false
          }
        createEvent(jobinfo)
        setData()
        setName()
        setTime()
        sort(copy)
        jobs = copy
        // console.log(jobs)
    }

    useDidUpdate(() => {
        changeModal(currentdate, index)
    }, [name, data, time]);

    const changeModal = (currentdate, index) =>{
        if(name && data && time)
            open()
        let currentJob = null
        let jsDate = null
        if(currentdate && index){
            currentJob = jobs.get(currentdate)[index]
            jsDate = currentdate.split("-").reverse().join("-")
        }
        let content = (
            <>
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
                />
                <Textarea
                    // disabled
                    label = "Mô tả"
                    value = {data}
                    onChange={e => {
                        setData(e.target.value)
                    }}
                />
                <Button 
                    ml = {"80%"}
                    onClick={() => {
                        deleteItem(currentdate, index)
                        addJob(time)
                        close()
                    }}
                    mt="md"
                >
                  Lưu
                </Button>
            </>
        )
        setContent(content)
    }
    const rows = [...Array(maxx)].map((_, i) => (
        <Table.Tr>
            {[...Array(7)].map((_, j) => (
                <Table.Td p={"0 0 0 10"}>
                    <Text>
                        {(() => {
                        let year = value.year()
                        if((year % 4 === 0 && year % 100 !== 0) || (year % 400 === 0))
                            numberofday[1] = 29
                        let date = value.date()+j-(6+value.day())%7
                        if(date <= 0)
                            date = date + numberofday[(value.month()+11)%12]
                        else if(date > numberofday[value.month()]) 
                            date = date - numberofday[value.month()]
                        if(date < 10)
                            date = '0'+date
                        date =  date+'-'+(value.month()+1)+'-'+value.year()
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
                                            changeModal(date, i)
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
                {content}
            </Modal>
        </MantineProvider>
    )
} 

export default List