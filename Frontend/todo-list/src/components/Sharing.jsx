import {Button, Select, Text, Modal, Stack, Group, ActionIcon} from "@mantine/core"
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
                size = "xs" 
                mr = {5} 
                ff = "Roboto"
                onClick = {() => {
                    open()
                    handleShare()
                }}
            >
                <Group >
                    <ActionIcon mr = {10}>
                        {(privacy === "Restricted") ? <IconLock/> : <IconWorld/>}
                    </ActionIcon>
                    <Text>
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