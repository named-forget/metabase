import React from "react";

import { List, WindowScroller, AutoSizer } from "react-virtualized";

const VirtualizedList = ({
  items,
  rowHeight,
  renderItem,
  useAutoSizerHeight = false,
}) => (
  <AutoSizer>
    {({ height: autosizerHeight, width }) => (
      <WindowScroller>
        {({ height, isScrolling, scrollTop }) => (
          <List
            autoHeight
            width={width}
            height={Math.min(
              useAutoSizerHeight ? autosizerHeight : height,
              rowHeight * items.length,
            )}
            isScrolling={isScrolling}
            rowCount={items.length}
            rowHeight={rowHeight}
            rowRenderer={({ index, key, style }) => (
              <div key={key} style={style}>
                {renderItem({ item: items[index], index })}
              </div>
            )}
            scrollTop={scrollTop}
          />
        )}
      </WindowScroller>
    )}
  </AutoSizer>
);

export default VirtualizedList;
