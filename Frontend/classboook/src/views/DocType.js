import React from 'react'
class DocType extends React.Component {
    render() {
        return (
            <>
                <script type="text/javascript" src="DocSelect.js"></script>
                <div className='dropdown'>
                    <div className='select'>
                        <span className='selected'>Lựa chọn loại tài liệu</span>
                        <div className='caret'></div>
                    </div>
                    <ul className='menu'>
                        <li>Sơ đồ tư duy</li>
                        <li>Văn bản</li>
                        <li>Câu hỏi</li>
                    </ul>
                </div>
            </>
        );
    }
}

export default DocType;