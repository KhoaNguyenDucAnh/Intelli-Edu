import {Button, Select, Text, Modal, Stack, Group, ActionIcon, ThemeIcon} from "@mantine/core"
import {useState, useCallback, useEffect} from "react"
import {modals} from "@mantine/modals"
import { useDidUpdate, useForceUpdate, useDisclosure} from "@mantine/hooks"
import {IconLock, IconWorld} from '@tabler/icons-react'

const Sharing = ({docname, jobs, date, index}) => {
    // const [opened, { open, close }] = useDisclosure(false);
    // const [privacy, setPrivacy] = useState("Restricted")
    // const [modalContent, setModalContent] = useState(null)

    const handleShare = () => {
        // let content = (
        //     <>
        //         <Stack>
        //             <Stack>

        //             </Stack>
        //             <Stack >
        //                 <Text>
        //                     Quyền truy cập
        //                 </Text>
        //                 <Group
        //                 >
                            
        //                     {(privacy === "Restricted") ? <IconLock/> : <IconWorld/>}
        //                     <Select
        //                             value = {privacy}
        //                             onChange = {setPrivacy}
        //                             data={["Restricted", "Public"]}
        //                             size = "xs"
        //                             mr = {50}
        //                     />
        //                 </Group>
        //             </Stack>
        //         </Stack>
        //     </> 
        // )
        // setModalContent(content)
        const id = jobs.get(date)[index].id
        const APIurl = `http://localhost:8080/api/v1/event/share/${id}`
        fetch(APIurl, {
            method: "POST",
            headers: {
                "Content-Type": 'application/json',
                "ngrok-skip-browser-warning": 1
            },
            body:{
                id: id
            }
        })
        .then(data => {
            console.log("POST data:", data.url)
            navigator.clipboard.writeText(data.url)
        })
    }
    return(
        <>
            <Button 
                size = {"xs"} 
                // mr = {5} 
                ff = "Roboto"
                onClick = {() => {
                    // open()
                    handleShare()
                }}
                color = "#bddcff"
                radius={100}
            >
                <Group w={100} justify="flex-start" spacing = {0}>
                    <ThemeIcon color = "transparent" size="xs">
                        <IconLock/>
                    </ThemeIcon>
                    <Text c = "black" ff="Montserrat" mt={2} fz="xs">
                        Chia sẻ 
                    </Text>
                </Group>
            </Button>
            {/* <Modal 
                title = {"Chia sẻ " + '"' + docname + '"'}
                opened={opened} 
                onClose={close}
            >
                {modalContent}
            </Modal> */}
        </>
    )
}

export default Sharing