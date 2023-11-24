import {Button, Select, Text, Modal, Stack, Group, ActionIcon, ThemeIcon} from "@mantine/core"
import {useState, useCallback, useEffect} from "react"
import {modals} from "@mantine/modals"
import { useDidUpdate, useForceUpdate, useDisclosure} from "@mantine/hooks"
import {IconLock, IconWorld} from '@tabler/icons-react'

const Sharing = ({docname, jobs}) => {
    const [opened, { open, close }] = useDisclosure(false);
    const [privacy, setPrivacy] = useState("Restricted")
    const [modalContent, setModalContent] = useState(null)
    
    useDidUpdate(() => {
        handleShare()
    }, [privacy]);
    const handleShare = () => {
        let content = (
            <>
                <Stack>
                    <Stack>

                    </Stack>
                    <Stack >
                        <Text>
                            Quyền truy cập
                        </Text>
                        <Group
                        >
                            
                            {(privacy === "Restricted") ? <IconLock/> : <IconWorld/>}
                            <Select
                                    value = {privacy}
                                    onChange = {setPrivacy}
                                    data={["Restricted", "Public"]}
                                    size = "xs"
                                    mr = {50}
                            />
                        </Group>
                    </Stack>
                </Stack>
            </> 
        )
        setModalContent(content)
    }
    return(
        <>
            <Button 
                size = "sm" 
                // mr = {5} 
                ff = "Roboto"
                onClick = {() => {
                    open()
                    handleShare()
                }}
                color = "#bddcff"
                radius={100}
            >
                <Group w={105} justify="center">
                    <ThemeIcon color = "transparent" >
                        {(privacy === "Restricted") ? <IconLock/> : <IconWorld/>}
                    </ThemeIcon>
                    <Text c = "black" ff="Montserrat" mt={2}>
                        Chia sẻ 
                    </Text>
                </Group>
            </Button>
            <Modal 
                title = {"Chia sẻ " + '"' + docname + '"'}
                opened={opened} 
                onClose={close}
            >
                {modalContent}
            </Modal>
        </>
    )
}

export default Sharing