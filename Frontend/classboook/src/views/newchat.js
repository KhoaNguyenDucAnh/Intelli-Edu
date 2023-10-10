import React from 'react'
import { Link } from "react-router-dom"
class chathistory extends React.Component {
    state = {
        MindMap: false,
        New: false,
        ChatHistory: true,
        MindMapColor: "#757575",
        NewColor: "#000",
        ChatHistoryColor: "#757575",
    }
    render() {
        let MindColor = this.state.MindMapColor
        let NColor = this.state.NewColor
        let HistoryColor = this.state.ChatHistoryColor
        return (
            <>
                <div className='Frame35'>
                    <div className='Frame20'>
                        <div className='Frame8'>
                            <Link to="/mindmap" className='Frame3' onClick={this.HandleMindMap}>
                                <svg className='icon1' xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" fill="none">
                                    <path d="M6.66667 23.6667V18.6454L12.08 22.1827C13.2456 22.9442 14.6077 23.3498 16 23.3498C17.3923 23.3498 18.7544 22.9442 19.92 22.1827L25.3333 18.6454V23.6667C25.3333 23.8831 25.2632 24.0936 25.1333 24.2667L25.132 24.268L25.1307 24.2707L25.1267 24.276L25.1147 24.2893L25.0987 24.3107L25.0813 24.3333L24.9667 24.468C24.8321 24.6207 24.6911 24.7675 24.544 24.908C24.1747 25.264 23.624 25.7267 22.8773 26.1853C21.3827 27.1053 19.1187 28 16 28C12.8813 28 10.6187 27.1067 9.12133 26.1853C8.52218 25.8202 7.96349 25.3925 7.45467 24.9093C7.25281 24.7146 7.0627 24.508 6.88533 24.2907L6.87333 24.2773L6.86933 24.2707V24.268C6.73872 24.0947 6.66763 23.8837 6.66667 23.6667ZM29.5467 13.504L18.8267 20.508C17.9862 21.0573 17.004 21.3498 16 21.3498C14.996 21.3498 14.0138 21.0573 13.1733 20.508L4 14.5147V21.6667C4 21.9319 3.89464 22.1863 3.70711 22.3738C3.51957 22.5613 3.26522 22.6667 3 22.6667C2.73478 22.6667 2.48043 22.5613 2.29289 22.3738C2.10536 22.1863 2 21.9319 2 21.6667V13.3333C2 13.216 2.02 13.104 2.05733 13C1.98224 12.7875 1.98116 12.5558 2.05425 12.3426C2.12734 12.1294 2.27032 11.9471 2.46 11.8253L13.212 4.93202C14.0441 4.3987 15.0117 4.11523 16 4.11523C16.9883 4.11523 17.9559 4.3987 18.788 4.93202L29.54 11.8253C29.6804 11.9155 29.7961 12.0394 29.8764 12.1857C29.9566 12.3319 29.9991 12.496 29.9997 12.6629C30.0004 12.8297 29.9593 12.9941 29.8801 13.141C29.801 13.288 29.6864 13.4127 29.5467 13.504Z" fill={MindColor} />
                                </svg>
                                <div className='Frame3Text' style={{ color: MindColor }}>Sơ đồ tư duy</div>
                            </Link>

                            <svg xmlns="http://www.w3.org/2000/svg" width="2" height="32" viewBox="0 0 2 32" fill="none">
                                <path d="M1 0V32" stroke="#E0E0E0" stroke-width="1.21942" />
                            </svg>

                            <Link to="/newchat" className='Frame8New' onClick={this.HandleNew}>
                                <svg className='icon2' xmlns="http://www.w3.org/2000/svg" width="25" height="25" viewBox="0 0 25 25" fill="none">
                                    <path d="M17.949 18.7857L18.624 18.1152C21.747 18.2172 21.795 18.1077 21.9322 17.7867L22.7902 15.698L22.854 15.5L22.7842 15.3267C22.7475 15.2352 22.6342 14.9622 20.6842 13.103V12.125C22.9342 9.95748 22.8862 9.84648 22.758 9.52923L21.9037 7.41873C21.7755 7.10223 21.7267 6.97848 18.6262 7.06323L17.9512 6.36573C18.0209 5.33831 17.9855 4.30645 17.8455 3.28623L17.7585 3.08898L15.528 2.11398C15.1995 1.96398 15.084 1.90998 12.978 4.20348L12.0315 4.18923C9.86323 1.87473 9.76048 1.91673 9.43573 2.04798L7.36198 2.88648C7.03723 3.01773 6.92248 3.06423 7.04848 6.21423L6.37873 6.88173C3.25723 6.77973 3.20923 6.89073 3.07348 7.21023L2.21398 9.29973L2.14648 9.49998L2.21698 9.67398C2.25373 9.76398 2.36248 10.034 4.31698 11.8955V12.8705C2.06698 15.038 2.11573 15.149 2.24473 15.467L3.09823 17.5797C3.22873 17.903 3.27523 18.0177 6.37498 17.9367L7.04998 18.638C6.98002 19.6638 7.01445 20.6941 7.15273 21.713L7.23973 21.9117L9.48448 22.8927C9.81073 23.0292 9.92398 23.078 12.024 20.7927L12.9705 20.8047C15.1417 23.1222 15.2527 23.0772 15.57 22.949L17.6392 22.1127C17.9662 21.983 18.0802 21.9372 17.949 18.7857ZM9.05548 13.8927C8.79378 13.2088 8.73989 12.4628 8.9006 11.7483C9.0613 11.0338 9.42943 10.3827 9.9588 9.87669C10.4882 9.37067 11.1552 9.03225 11.8762 8.90391C12.5972 8.77557 13.34 8.86302 14.0115 9.15529C14.6829 9.44756 15.2531 9.93162 15.6505 10.5468C16.0479 11.1619 16.2548 11.8807 16.2452 12.6129C16.2355 13.3452 16.0099 14.0583 15.5965 14.6628C15.1831 15.2673 14.6004 15.7362 13.9215 16.0107C12.9952 16.373 11.9631 16.3535 11.0511 15.9565C10.1392 15.5596 9.4216 14.8175 9.05548 13.8927Z" fill={NColor} />
                                </svg>
                                <div className='Frame8NewText' style={{ color: NColor }}>Thêm mới </div>
                            </Link>

                            <svg xmlns="http://www.w3.org/2000/svg" width="2" height="32" viewBox="0 0 2 32" fill="none">
                                <path d="M1 0V32" stroke="#E0E0E0" stroke-width="1.21942" />
                            </svg>

                            <Link to="/chathistory" className='Frame9' onClick={this.HandleHistory}>
                                <svg className='icon3' xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" fill="none">
                                    <path d="M12.64 4.62498C10.3819 3.54686 7.22563 3.01873 3 2.99998C2.60148 2.99457 2.21078 3.11078 1.88 3.33311C1.6085 3.51663 1.38624 3.76405 1.2328 4.05362C1.07935 4.34319 0.999407 4.66602 1 4.99373V22.875C1 24.0837 1.86 24.9956 3 24.9956C7.44188 24.9956 11.8975 25.4106 14.5663 27.9331C14.6028 27.9678 14.6486 27.9909 14.6982 27.9998C14.7478 28.0086 14.7988 28.0026 14.8451 27.9826C14.8913 27.9626 14.9306 27.9295 14.9581 27.8874C14.9857 27.8453 15.0002 27.796 15 27.7456V6.67623C15.0001 6.53408 14.9697 6.39356 14.9108 6.26417C14.852 6.13478 14.766 6.01954 14.6588 5.92623C14.0474 5.40363 13.3684 4.96593 12.64 4.62498ZM30.12 3.33123C29.7891 3.10946 29.3983 2.9939 29 2.99998C24.7744 3.01873 21.6181 3.54436 19.36 4.62498C18.6316 4.96531 17.9524 5.40215 17.3406 5.92373C17.2336 6.0172 17.1479 6.13249 17.0891 6.26186C17.0304 6.39122 17 6.53166 17 6.67373V27.7444C17 27.7927 17.0142 27.84 17.041 27.8804C17.0677 27.9207 17.1058 27.9522 17.1504 27.9709C17.195 27.9897 17.2441 27.9949 17.2916 27.9858C17.3392 27.9767 17.3829 27.9538 17.4175 27.92C19.0219 26.3262 21.8375 24.9937 29.0025 24.9944C29.5329 24.9944 30.0416 24.7836 30.4167 24.4086C30.7918 24.0335 31.0025 23.5248 31.0025 22.9944V4.99436C31.0032 4.666 30.9231 4.34251 30.7692 4.05246C30.6153 3.7624 30.3923 3.5147 30.12 3.33123Z" fill={HistoryColor} />
                                </svg>
                                <div className='Frame9Text' style={{ color: HistoryColor }}>Lịch sử  trò chuyện  </div>
                            </Link>
                        </div>
                    </div>
                </div>
                <div className='Main'>
                    <div className='Frame35New'>
                        <div className='Frame39'>
                            <div className='image54' />
                            <div className='Frame20New'>
                                <div className='Frame18'>
                                    <div className='Frame18Text'>
                                        Chưa có cuộc trò chuyện được lưu
                                    </div>
                                    <div className='Frame6'>
                                        <button className='Frame2New'>
                                            <div className='Frame2NewText'>Đến chat bot</div>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div >
            </>
        );
    }
}

export default chathistory;