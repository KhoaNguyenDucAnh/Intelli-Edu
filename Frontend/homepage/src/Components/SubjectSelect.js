import React from 'react'
import { Select } from '@mantine/core';
import '@mantine/core/styles.css'

class SubjectSelect extends React.Component {
    render() {
        return (
            <>
                <Select
                    ml={30}
                    size={"lg"}
                    placeholder="Chọn môn học"
                    checkIconPosition="right"

                    data={['Toán Học', 'Vật Lí', 'Hóa Học', 'Sinh Học', 'Ngữ Văn', 'Ngoại Ngữ', 'Lịch Sử', 'Địa Lý', 'Công Nghệ', 'GDCD', 'GDQP', 'Tin Học']}
                />
            </>
        );
    }
}

export default SubjectSelect;