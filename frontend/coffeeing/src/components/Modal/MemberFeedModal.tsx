import { Dialog, Transition } from '@headlessui/react';
import FeedCard from 'components/Feed/FeedCard';
import React, { Fragment, useRef, useState } from 'react';
import { deleteFeeds, postFeedLike } from 'service/feed/feed';
import { FeedDetail } from 'service/feed/types';
import { FeedEditModal } from './FeedEditModal';
import { useDebounce } from '@react-hooks-hub/use-debounce';
import { Tag } from 'service/search/types';
import { getTagsByKeyword } from 'service/search/search';

type MemberFeedModalProps = {
  isOpen: boolean;
  feedDetail: FeedDetail;
  setters: MemberFeedModalSetters;
};

type MemberFeedModalSetters = {
  setIsOpen: (open: boolean) => void;
};

export const MemberFeedModal = ({
  isOpen,
  feedDetail,
  setters,
}: MemberFeedModalProps) => {
  const [isEditModalOpen, setIsEditModalOpen] = useState<boolean>(false);
  const [suggestions, setSuggestions] = useState<Tag[]>([]);
  const [editTarget, setEditTarget] = useState<FeedDetail | null>(null);

  const deleteEventHandler = async (feedId: number) => {
    const res = await deleteFeeds(feedId);
    if (res) {
      window.location.reload();
    }
  };
  const likeToggleEventHandler = async (feedId: number) => {
    const res = await postFeedLike(feedId);
    return res ? res.result : null;
  };
  const editHandler = (feedDetail: FeedDetail) => {
    setIsEditModalOpen(true);
    setEditTarget(feedDetail);
  };
  const debouncedSearch = useDebounce((keyword: string) => {
    changeSuggestions(keyword);
  }, 300);
  const changeSuggestions = async (keyword: string) => {
    const result = await getTagsByKeyword(keyword);
    if (result) {
      setSuggestions(result.tags);
    }
  };

  return (
    <>
      <Transition.Root show={isOpen} as={Fragment}>
        <Dialog as="div" className="relative z-5" onClose={setters.setIsOpen}>
          <Transition.Child
            as={Fragment}
            enter="ease-out duration-300"
            enterFrom="opacity-0"
            enterTo="opacity-100"
            leave="ease-in duration-200"
            leaveFrom="opacity-100"
            leaveTo="opacity-0"
          >
            <div className="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" />
          </Transition.Child>

          <div className="fixed inset-0 z-10 w-screen overflow-y-auto">
            <div className="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0">
              <Transition.Child
                as={Fragment}
                enter="ease-out duration-300"
                enterFrom="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
                enterTo="opacity-100 translate-y-0 sm:scale-100"
                leave="ease-in duration-200"
                leaveFrom="opacity-100 translate-y-0 sm:scale-100"
                leaveTo="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
              >
                <Dialog.Panel className="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg">
                  <FeedCard
                    feedDetail={feedDetail}
                    deleteEventHandler={deleteEventHandler}
                    likeToggleEventHandler={likeToggleEventHandler}
                    editHandler={editHandler}
                  />
                </Dialog.Panel>
              </Transition.Child>
            </div>
          </div>
          <FeedEditModal
            isOpen={isEditModalOpen}
            setIsOpen={setIsEditModalOpen}
            suggestions={suggestions}
            debouncedSearch={debouncedSearch}
            feedDetail={editTarget}
            setEditTarget={setEditTarget}
          />
        </Dialog>
      </Transition.Root>
    </>
  );
};
