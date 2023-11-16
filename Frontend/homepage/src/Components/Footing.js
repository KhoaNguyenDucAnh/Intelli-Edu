import './Footing.css'
import React from 'react'
class Heading extends React.Component {
    render() {
        return (
            <>
                <div className="Ending">
                    <div className='CopyrightContent'>
                        <div className='CopyrightSentence'>Một dự án vì cộng đồng nhằm nâng cao khả năng tự học của học sinh được thực hiện bởi IntelliEdu</div>
                        <svg className='CopyrightLine' xmlns="http://www.w3.org/2000/svg" width="64.166666666666666666666666666667vw" height="2" viewBox="0 0 1232 2" fill="none">
                            <path d="M0 1L1232 0.999946" stroke="#E0E0E0" stroke-width="1.20466" />
                        </svg>
                        <div className='Contact'>
                            <div className='Logos'>
                                <a href="https://github.com/KhoaNguyenDucAnh/Intelli-Edu">

                                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="20" viewBox="0 0 25 20" fill="none">
                                        <path d="M9.61919 13.7617C9.61919 14.8432 9.05578 16.613 7.72223 16.613C6.38867 16.613 5.82527 14.8432 5.82527 13.7617C5.82527 12.6801 6.38867 10.9104 7.72223 10.9104C9.05578 10.9104 9.61919 12.6801 9.61919 13.7617ZM24.8104 11.1484C24.8104 12.7992 24.645 14.5483 23.9058 16.0645C21.9468 20.0284 16.5609 19.9352 12.705 19.9352C8.787 19.9352 3.08062 20.0749 1.0441 16.0645C0.289454 14.5638 0 12.7992 0 11.1484C0 8.98016 0.718467 6.93093 2.14506 5.26982C1.87628 4.4522 1.74706 3.59318 1.74706 2.74452C1.74706 1.63193 2.00034 1.07305 2.50171 0.0639648C4.84319 0.0639648 6.34215 0.529698 8.12539 1.9269C9.62435 1.56983 11.1647 1.40941 12.7101 1.40941C14.1057 1.40941 15.5116 1.55948 16.8659 1.8855C18.6233 0.503823 20.1222 0.0639648 22.4379 0.0639648C22.9444 1.07305 23.1925 1.63193 23.1925 2.74452C23.1925 3.59318 23.0581 4.43668 22.7945 5.23877C24.2159 6.91541 24.8104 8.98016 24.8104 11.1484ZM21.4868 13.7617C21.4868 11.4899 20.1067 9.48729 17.6877 9.48729C16.7108 9.48729 15.7753 9.66323 14.7932 9.79778C14.023 9.9168 13.2529 9.96337 12.462 9.96337C11.6764 9.96337 10.9062 9.9168 10.1309 9.79778C9.16433 9.66323 8.21843 9.48729 7.23636 9.48729C4.81735 9.48729 3.43727 11.4899 3.43727 13.7617C3.43727 18.3052 7.593 19.0038 11.2112 19.0038H13.7026C17.3362 19.0038 21.4868 18.3103 21.4868 13.7617ZM17.2174 10.9104C15.8838 10.9104 15.3204 12.6801 15.3204 13.7617C15.3204 14.8432 15.8838 16.613 17.2174 16.613C18.5509 16.613 19.1143 14.8432 19.1143 13.7617C19.1143 12.6801 18.5509 10.9104 17.2174 10.9104Z" fill="#BABABA" />
                                    </svg>
                                </a>
                                <a href="https://www.facebook.com/vtvgirl">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="29" height="20" viewBox="0 0 29 20" fill="none">
                                        <path d="M8.46828 11.4863H16.4048V19.1812H13.1335L12.7432 15.8913C12.3715 17.0685 11.7086 17.9421 10.7544 18.5121C9.80033 19.0821 8.63556 19.367 7.26014 19.367C6.00864 19.367 4.89343 19.0883 3.91453 18.5307C2.94802 17.9731 2.18597 17.1738 1.62836 16.133C1.08315 15.0921 0.810547 13.8778 0.810547 12.49C0.810547 10.2719 1.47347 8.5186 2.79933 7.22992C4.12518 5.94124 6.18831 5.2969 8.98871 5.2969C10.5872 5.2969 11.9936 5.55711 13.2079 6.07754C14.4346 6.59797 15.4755 7.40339 16.3305 8.49381C15.7729 8.77881 14.6824 9.3488 13.0592 10.2038L12.5945 10.464C12.1113 9.98075 11.5475 9.62141 10.9031 9.38598C10.2588 9.13815 9.57729 9.01424 8.8586 9.01424C7.66905 9.01424 6.78308 9.33022 6.2007 9.96216C5.61832 10.5817 5.32712 11.35 5.32712 12.2669C5.32712 13.2582 5.6369 14.0574 6.25646 14.6646C6.88841 15.2718 7.84872 15.5754 9.1374 15.5754C9.69501 15.5754 10.234 15.4948 10.7544 15.3337C11.2873 15.1726 11.7333 14.8938 12.0927 14.4973H8.46828V11.4863Z" fill="#BABABA" />
                                        <path d="M28.7092 4.53578C26.7638 4.47383 24.8742 4.43665 23.0403 4.42426V14.1265H18.3936V4.42426C16.5473 4.43665 14.6639 4.47383 12.7432 4.53578V0.632571H28.7092V4.53578Z" fill="#BABABA" />
                                    </svg>

                                </a>
                                <a href="https://youtu.be/GX8Hg6kWQYI?si=cutQ0mtRw4_fnu7_">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="26" height="18" viewBox="0 0 26 18" fill="none">
                                        <path fill-rule="evenodd" clip-rule="evenodd" d="M22.9874 0.684539C24.0857 0.998319 24.8701 1.78277 25.1839 2.88101C25.8115 4.92058 25.8115 8.99973 25.8115 8.99973C25.8115 8.99973 25.8115 13.0789 25.3408 15.1185C25.027 16.2167 24.2425 17.0011 23.1443 17.3149C21.1047 17.7856 13.2602 17.7856 13.2602 17.7856C13.2602 17.7856 5.2588 17.7856 3.37612 17.3149C2.27788 17.0011 1.49344 16.2167 1.17966 15.1185C0.708989 13.0789 0.708984 8.99973 0.708984 8.99973C0.708984 8.99973 0.70898 4.92058 1.02276 2.88101C1.33654 1.78277 2.12101 0.998319 3.21924 0.684539C5.25882 0.213867 13.1033 0.213867 13.1033 0.213867C13.1033 0.213867 21.1048 0.213867 22.9874 0.684539ZM10.75 5.92363C10.75 5.6165 11.0818 5.42396 11.3484 5.57633L16.7316 8.65243C17.0003 8.80599 17.0003 9.19347 16.7316 9.34703L11.3484 12.4231C11.0818 12.5755 10.75 12.383 10.75 12.0758V5.92363Z" fill="#BABABA" />
                                    </svg>

                                </a>

                            </div>
                            <div className='ContactEmail'>Liên hệ: Intelliedu2023@gmail.com</div>
                        </div>
                    </div>
                </div>
            </>
        );
    }
}

export default Heading;