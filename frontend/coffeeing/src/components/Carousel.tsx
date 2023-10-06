import React, { Component } from 'react';
import Slider from 'react-slick';
import { BeanCard } from './BeanCard';
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

export type curationListProps = {
  id: number;
  title: string;
  subtitle: string;
  imageUrl: string;
};

type CarouselProps = {
  curationList: curationListProps[];
  isCapsule: boolean;
};

const Carousel = (props: CarouselProps) => {
  const { curationList, isCapsule } = props;

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

  return (
    <Slider {...settings} className="w-full flex justify-center pl-10">
      {curationList.map((capsule) => (
        <BeanCard
          subtitle={capsule.subtitle}
          id={capsule.id}
          name={capsule.title}
          imgLink={capsule.imageUrl}
          isCapsule={isCapsule}
          key={capsule.title}
        />
      ))}
    </Slider>
  );
};

export default Carousel;
