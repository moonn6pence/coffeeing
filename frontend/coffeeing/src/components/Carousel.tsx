import React, { Component } from 'react';
import Slider from 'react-slick';
import { CapsuleCard } from './CapsuleCard';
import eximg from 'assets/google_logo.png';
import Next from 'assets/nextarrow.png';
import Prev from 'assets/prevarrow.png';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';

function NextArrow(props: any) {
  const { className, onClick } = props;
  return (
    <img
      src={Next}
      className={className}
      style={{ display: 'block', width: '40px', height: '40px', right: '0' }}
      onClick={onClick}
    />
  );
}

function PrevArrow(props: any) {
  const { className, onClick } = props;
  return (
    <img
      src={Prev}
      className={className}
      style={{ display: 'block', width: '40px', height: '40px', left: '0' }}
      onClick={onClick}
    />
  );
}

export default class Carousel extends Component {
  render() {
    const settings = {
      dots: false,
      infinite: true,
      speed: 500,
      slidesToShow: 4,
      slidesToScroll: 4,
      initialSlide: 0,
      nextArrow: <NextArrow />,
      prevArrow: <PrevArrow />,
      responsive: [
        {
          breakpoint: 1024,
          settings: {
            slidesToShow: 3,
            slidesToScroll: 3,
            infinite: true,
            dots: false,
          },
        },
        {
          breakpoint: 600,
          settings: {
            slidesToShow: 2,
            slidesToScroll: 2,
            initialSlide: 2,
          },
        },
        {
          breakpoint: 480,
          settings: {
            slidesToShow: 1,
            slidesToScroll: 1,
          },
        },
      ],
    };

    // 더미데이터, 나중에 실제 데이터 받아와서 연결해줘야함
    const capsuleList = [
      {
        name: '아르페지오',
        subtitle: '네스프레소',
        capsule_id: 1,
        imgLink: '/',
      },
      { name: '니카라과', subtitle: '네스프레소', capsule_id: 2, imgLink: '/' },
      { name: '코지', subtitle: '네스프레소', capsule_id: 3, imgLink: '/' },
      {
        name: '인도네시아',
        subtitle: '네스프레소',
        capsule_id: 4,
        imgLink: '/',
      },
      { name: '볼루토', subtitle: '네스프레소', capsule_id: 5, imgLink: '/' },
      { name: '베네치아', subtitle: '네스프레소', capsule_id: 6, imgLink: '/' },
      { name: '리반토', subtitle: '네스프레소', capsule_id: 7, imgLink: '/' },
      {
        name: '리스트레토',
        subtitle: '네스프레소',
        capsule_id: 8,
        imgLink: '/',
      },
    ];

    return (
      <Slider {...settings} className="w-320 flex justify-center pl-10">
        {capsuleList.map((capsule) => (
          <CapsuleCard
            subtitle={capsule.subtitle}
            capsule_id={capsule.capsule_id}
            name={capsule.name}
            imgLink={eximg}
            key={capsule.name}
          />
        ))}
      </Slider>
    );
  }
}
