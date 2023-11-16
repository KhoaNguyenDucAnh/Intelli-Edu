import React from 'react'
import { Select } from '@mantine/core';
import '@mantine/core/styles.css'
class DocType extends React.Component {
    render() {
        return (
            <>
                <Select
                    ml={30}
                    size={"lg"}
                    placeholder="Chọn loại tài liệu"
                    checkIconPosition="right"

                    data={['Sơ đồ tư duy', 'Văn bản', 'Câu hỏi']}
                />
            </>
        );
    }
}

export default DocType;