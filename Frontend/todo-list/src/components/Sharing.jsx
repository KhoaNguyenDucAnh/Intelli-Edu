import {Button, Select, Text, Modal, Stack, Group, ActionIcon, ThemeIcon} from "@mantine/core"
import {useState, useCallback, useEffect} from "react"
import { notifications } from '@mantine/notifications';
import { useDidUpdate, useForceUpdate, useDisclosure} from "@mantine/hooks"
import {IconLock, IconWorld} from '@tabler/icons-react'
// import '@mantine/notifications/styles.css';

const Sharing = ({docname, jobs, date, index}) => {

    const handleShare = () => {
        const id = jobs.get(date)[index].id
        const APIurl = `http://localhost:8080/api/v1/event/share/${id}`
        fetch(APIurl, {
            method: "POST",
            headers: {
                "Content-Type": 'application/json'
            }
        })
        .then(response => response.text())
        .then(data => {
            navigator.clipboard.writeText(data)
        })
        notifications.show({
            title: 'Shared '+jobs.get(date)[index].name,
            message: 'Share link copied to clipboard',
            color: 'teal',
        })
    }
    return(
        <>
            <Button 
                size = {"xs"} 
                // mr = {5} 
                ff = "Roboto"
                onClick = {() => {
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
        </>
    )
}

export default Sharing